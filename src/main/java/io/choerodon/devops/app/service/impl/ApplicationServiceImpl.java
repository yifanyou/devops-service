package io.choerodon.devops.app.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.*;

import com.google.gson.Gson;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.choerodon.asgard.saga.annotation.Saga;
import io.choerodon.asgard.saga.dto.StartInstanceDTO;
import io.choerodon.asgard.saga.feign.SagaClient;
import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.core.convertor.ConvertPageHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.api.dto.*;
import io.choerodon.devops.api.validator.ApplicationValidator;
import io.choerodon.devops.app.service.ApplicationService;
import io.choerodon.devops.domain.application.entity.*;
import io.choerodon.devops.domain.application.entity.gitlab.CommitE;
import io.choerodon.devops.domain.application.entity.gitlab.GitlabGroupE;
import io.choerodon.devops.domain.application.entity.gitlab.GitlabGroupMemberE;
import io.choerodon.devops.domain.application.entity.gitlab.GitlabUserE;
import io.choerodon.devops.domain.application.event.GitlabProjectPayload;
import io.choerodon.devops.domain.application.factory.ApplicationFactory;
import io.choerodon.devops.domain.application.repository.*;
import io.choerodon.devops.domain.application.valueobject.Organization;
import io.choerodon.devops.domain.application.valueobject.ProjectHook;
import io.choerodon.devops.infra.common.util.*;
import io.choerodon.devops.infra.common.util.enums.AccessLevel;
import io.choerodon.devops.infra.config.CiYamlConfig;
import io.choerodon.devops.infra.dataobject.gitlab.GitlabProjectDO;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

/**
 * Created by younger on 2018/3/28.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final String MASTER = "master";
    private static final String APPLICATION = "application";

    private Gson gson = new Gson();

    @Value("${services.gitlab.url}")
    private String gitlabUrl;
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${services.sonarqube.url}")
    private String sonarqubeUrl;
    @Value("${services.gateway.url}")
    private String gatewayUrl;

    @Autowired
    private GitlabRepository gitlabRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private IamRepository iamRepository;
    @Autowired
    private ApplicationTemplateRepository applicationTemplateRepository;
    @Autowired
    private DevopsProjectRepository devopsProjectRepository;
    @Autowired
    private GitUtil gitUtil;
    @Autowired
    private CiYamlConfig ciYamlConfig;
    @Autowired
    private GitlabUserRepository gitlabUserRepository;
    @Autowired
    private UserAttrRepository userAttrRepository;
    @Autowired
    private GitlabGroupMemberRepository gitlabGroupMemberRepository;
    @Autowired
    private DevopsGitRepository devopsGitRepository;
    @Autowired
    private SagaClient sagaClient;

    @Override
    @Saga(code = "devops-create-gitlab-project",
            description = "devops create GitLab project", inputSchema = "{}")
    public ApplicationRepDTO create(Long projectId, ApplicationDTO applicationDTO) {
        UserAttrE userAttrE = userAttrRepository.queryById(TypeUtil.objToLong(GitUserNameUtil.getUserId()));
        ApplicationValidator.checkApplication(applicationDTO);
        ProjectE projectE = iamRepository.queryIamProject(projectId);
        Organization organization = iamRepository.queryOrganizationById(projectE.getOrganization().getId());
        ApplicationE applicationE = ConvertHelper.convert(applicationDTO, ApplicationE.class);
        applicationE.initProjectE(projectId);
        applicationRepository.checkName(applicationE);
        applicationRepository.checkCode(applicationE);
        applicationE.initActive(true);
        applicationE.initSynchro(false);
        GitlabGroupE gitlabGroupE = devopsProjectRepository.queryDevopsProject(applicationE.getProjectE().getId());
        if (gitlabGroupE == null) {
            throw new CommonException("error.group.not.sync");
        }
        GitlabGroupMemberE groupMemberE = gitlabGroupMemberRepository.getUserMemberByUserId(
                gitlabGroupE.getGitlabGroupId(),
                TypeUtil.objToInteger(userAttrE.getGitlabUserId()));
        if (groupMemberE == null || groupMemberE.getAccessLevel() != AccessLevel.OWNER.toValue()) {
            throw new CommonException("error.user.not.owner");
        }
        GitlabProjectPayload gitlabProjectPayload = new GitlabProjectPayload();
        gitlabProjectPayload.setType(APPLICATION);
        gitlabProjectPayload.setPath(applicationDTO.getCode());
        gitlabProjectPayload.setOrganizationId(organization.getId());
        gitlabProjectPayload.setUserId(TypeUtil.objToInteger(userAttrE.getGitlabUserId()));
        gitlabProjectPayload.setGroupId(gitlabGroupE.getGitlabGroupId());

        if (applicationRepository.create(applicationE).getId() == null) {
            throw new CommonException("error.application.create.insert");
        }
        String input = gson.toJson(gitlabProjectPayload);
        sagaClient.startSaga("devops-create-gitlab-project", new StartInstanceDTO(input, "", ""));

        return ConvertHelper.convert(applicationRepository.queryByCode(applicationE.getCode(),
                applicationE.getProjectE().getId()), ApplicationRepDTO.class);
    }

    @Override
    public ApplicationRepDTO query(Long projectId, Long applicationId) {
        ProjectE projectE = iamRepository.queryIamProject(projectId);
        Organization organization = iamRepository.queryOrganizationById(projectE.getOrganization().getId());
        ApplicationE applicationE = applicationRepository.query(applicationId);
        String urlSlash = gitlabUrl.endsWith("/") ? "" : "/";
        applicationE.initGitlabProjectEByUrl(gitlabUrl + urlSlash
                + organization.getCode() + "-" + projectE.getCode() + "/"
                + applicationE.getCode() + ".git");
        return ConvertHelper.convert(applicationE, ApplicationRepDTO.class);
    }

    @Override
    public Boolean update(Long projectId, ApplicationUpdateDTO applicationUpdateDTO) {
        ApplicationE applicationE = ConvertHelper.convert(applicationUpdateDTO, ApplicationE.class);
        applicationE.initProjectE(projectId);
        applicationRepository.checkName(applicationE);
        if (applicationRepository.update(applicationE) != 1) {
            throw new CommonException("error.application.update");
        }
        return true;
    }

    @Override
    public Boolean active(Long applicationId, Boolean active) {
        if (!active) {
            applicationRepository.checkAppCanDisable(applicationId);
        }
        ApplicationE applicationE = applicationRepository.query(applicationId);
        applicationE.initActive(active);
        if (applicationRepository.update(applicationE) != 1) {
            throw new CommonException("error.application.active");
        }
        return true;
    }

    @Override
    public Page<ApplicationRepDTO> listByOptions(Long projectId, Boolean isActive, Boolean hasVersion,
                                                 PageRequest pageRequest, String params) {
        Page<ApplicationE> applicationES =
                applicationRepository.listByOptions(projectId, isActive, hasVersion, pageRequest, params);
        ProjectE projectE = iamRepository.queryIamProject(projectId);
        Organization organization = iamRepository.queryOrganizationById(projectE.getOrganization().getId());
        String urlSlash = gitlabUrl.endsWith("/") ? "" : "/";
        applicationES.getContent().parallelStream()
                .forEach(t -> {
                    if (t.getGitlabProjectE() != null && t.getGitlabProjectE().getId() != null) {
                        t.initGitlabProjectEByUrl(gitlabUrl + urlSlash
                                + organization.getCode() + "-" + projectE.getCode() + "/"
                                + t.getCode() + ".git");
                        if (!sonarqubeUrl.equals("")) {
                            Integer result = 0;
                            try {
                                result = HttpClientUtil.getSonar(
                                        sonarqubeUrl.endsWith("/")
                                                ? sonarqubeUrl
                                                : String.format(
                                                "%s/api/project_links/search?projectKey=%s-%s:%s",
                                                sonarqubeUrl,
                                                organization.getCode(),
                                                projectE.getCode(),
                                                t.getCode()
                                        ));
                                if (result.equals(HttpStatus.OK.value())) {
                                    t.initSonarUrl(sonarqubeUrl.endsWith("/") ? sonarqubeUrl : sonarqubeUrl + "/"
                                            + "dashboard?id="
                                            + organization.getCode() + "-" + projectE.getCode() + ":"
                                            + t.getCode());
                                }
                            } catch (Exception e) {
                                t.initSonarUrl(null);
                            }
                        }
                    }
                });
        return ConvertPageHelper.convertPage(applicationES, ApplicationRepDTO.class);
    }

    @Override
    public Page<ApplicationRepDTO> listCodeRepository(Long projectId, PageRequest pageRequest, String params) {
        Page<ApplicationE> applicationES = applicationRepository.listCodeRepository(projectId, pageRequest, params);
        applicationES.forEach(t -> t.initGitlabProjectEByUrl(devopsGitRepository.getGitlabUrl(projectId, t.getId())));
        return ConvertPageHelper.convertPage(applicationES, ApplicationRepDTO.class);
    }

    @Override
    public List<ApplicationRepDTO> listByActive(Long projectId) {
        return ConvertHelper.convertList(applicationRepository.listByActive(projectId), ApplicationRepDTO.class);
    }

    @Override
    public List<ApplicationRepDTO> listAll(Long projectId) {
        return ConvertHelper.convertList(applicationRepository.listAll(projectId), ApplicationRepDTO.class);
    }

    @Override
    public void checkName(Long projectId, String name) {
        ApplicationE applicationE = ApplicationFactory.createApplicationE();
        applicationE.initProjectE(projectId);
        applicationE.setName(name);
        applicationRepository.checkName(applicationE);
    }

    @Override
    public void checkCode(Long projectId, String code) {
        ApplicationE applicationE = ApplicationFactory.createApplicationE();
        applicationE.initProjectE(projectId);
        applicationE.setCode(code);
        applicationRepository.checkCode(applicationE);
    }

    @Override
    public List<ApplicationTemplateRepDTO> listTemplate(Long projectId) {
        ProjectE projectE = iamRepository.queryIamProject(projectId);
        return ConvertHelper.convertList(applicationTemplateRepository.list(projectE.getOrganization().getId()),
                ApplicationTemplateRepDTO.class);
    }

    @Override
    public void operationApplication(GitlabProjectPayload gitlabProjectPayload) {
        GitlabProjectDO gitlabProjectDO = gitlabRepository.createProject(gitlabProjectPayload.getGroupId(),
                gitlabProjectPayload.getPath(),
                gitlabProjectPayload.getUserId(), false);
        gitlabProjectPayload.setGitlabProjectId(gitlabProjectDO.getId());

        GitlabGroupE gitlabGroupE = devopsProjectRepository.queryByGitlabGroupId(
                TypeUtil.objToInteger(gitlabProjectPayload.getGroupId()));
        ApplicationE applicationE = applicationRepository.queryByCode(gitlabProjectPayload.getPath(),
                gitlabGroupE.getProjectE().getId());
        ProjectE projectE = iamRepository.queryIamProject(gitlabGroupE.getProjectE().getId());
        Organization organization = iamRepository.queryOrganizationById(projectE.getOrganization().getId());
        if (applicationE.getApplicationTemplateE() != null) {
            ApplicationTemplateE applicationTemplateE = applicationTemplateRepository.query(
                    applicationE.getApplicationTemplateE().getId());
            String applicationDir = APPLICATION + System.currentTimeMillis();
            //拉取模板
            String repoUrl = applicationTemplateE.getRepoUrl();
            String type = applicationTemplateE.getCode();
            boolean teamplateType = true;
            if (applicationTemplateE.getOrganization().getId() != null) {
                repoUrl = repoUrl.startsWith("/") ? repoUrl.substring(1, repoUrl.length()) : repoUrl;
                repoUrl = !gitlabUrl.endsWith("/") ? gitlabUrl + "/" + repoUrl : gitlabUrl + repoUrl;
                type = MASTER;
                teamplateType = false;
            }
            Git git = gitUtil.clone(applicationDir, type,
                    repoUrl);
            //渲染模板里面的参数
            try {
                File file = new File(gitUtil.getWorkingDirectory(applicationDir));
                Map<String, String> params = new HashMap<>();
                params.put("{{group.name}}", organization.getCode() + "-" + projectE.getCode());
                params.put("{{service.code}}", applicationE.getCode());
                FileUtil.replaceReturnFile(file, params);
            } catch (Exception e) {
                //删除模板
                gitUtil.deleteWorkingDirectory(applicationDir);
                throw new CommonException(e.getMessage());
            }

            List<String> tokens = gitlabRepository.listTokenByUserId(gitlabProjectPayload.getGitlabProjectId(),
                    applicationDir, gitlabProjectPayload.getUserId());
            String accessToken = "";
            if (tokens.isEmpty()) {
                accessToken = gitlabRepository.createToken(gitlabProjectPayload.getGitlabProjectId(),
                        applicationDir, gitlabProjectPayload.getUserId());
            } else {
                accessToken = tokens.get(tokens.size() - 1);
            }
            repoUrl = !gitlabUrl.endsWith("/") ? gitlabUrl + "/" : gitlabUrl;
            applicationE.initGitlabProjectEByUrl(repoUrl
                    + organization.getCode() + "-" + projectE.getCode() + "/"
                    + applicationE.getCode() + ".git");
            GitlabUserE gitlabUserE = gitlabUserRepository.getGitlabUserByUserId(gitlabProjectPayload.getUserId());
            gitUtil.push(git, applicationDir, applicationE.getGitlabProjectE().getRepoURL(),
                    gitlabUserE.getUsername(), accessToken, teamplateType);
            gitlabRepository.createProtectBranch(gitlabProjectPayload.getGitlabProjectId(), MASTER,
                    AccessLevel.MASTER.toString(), AccessLevel.MASTER.toString(), gitlabProjectPayload.getUserId());
            CommitE commitE;
            try {
                commitE = devopsGitRepository.getCommit(
                        gitlabProjectPayload.getGitlabProjectId(), MASTER, gitlabProjectPayload.getUserId());
            } catch (Exception e) {
                commitE = new CommitE();
            }
            DevopsBranchE devopsBranchE = new DevopsBranchE();
            devopsBranchE.setUserId(TypeUtil.objToLong(gitlabProjectPayload.getUserId()));
            devopsBranchE.setApplicationE(applicationE);
            devopsBranchE.setBranchName(MASTER);
            devopsBranchE.setCheckoutCommit(commitE.getId());
            Date date = DateUtil.changeTimeZone(
                    commitE.getCommittedDate(), TimeZone.getTimeZone("GMT"), TimeZone.getDefault());
            devopsBranchE.setCheckoutDate(date);
            devopsBranchE.setLastCommitUser(TypeUtil.objToLong(gitlabProjectPayload.getUserId()));
            devopsBranchE.setLastCommitMsg(commitE.getMessage());
            devopsBranchE.setLastCommitDate(date);
            devopsBranchE.setLastCommit(commitE.getId());
            devopsGitRepository.createDevopsBranch(devopsBranchE);
        }
        try {
            String token = GenerateUUID.generateUUID();
            gitlabRepository.addVariable(gitlabProjectPayload.getGitlabProjectId(), "Token",
                    token,
                    false, gitlabProjectPayload.getUserId());
            applicationE.setToken(token);
            applicationE.initGitlabProjectE(
                    TypeUtil.objToInteger(gitlabProjectPayload.getGitlabProjectId()));
            applicationE.initSynchro(true);
            ProjectHook projectHook = ProjectHook.allHook();
            projectHook.setEnableSslVerification(true);
            projectHook.setProjectId(gitlabProjectPayload.getGitlabProjectId());
            projectHook.setToken(token);
            String uri = !gatewayUrl.endsWith("/") ? gatewayUrl + "/" : gatewayUrl;
            uri += "devops/webhook";
            projectHook.setUrl(uri);
            applicationE.initHookId(TypeUtil.objToLong(gitlabRepository.createWebHook(
                    gitlabProjectPayload.getGitlabProjectId(), gitlabProjectPayload.getUserId(), projectHook)
                    .getId()));
            if (applicationRepository.update(applicationE) != 1) {
                throw new CommonException("error.application.update");
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Boolean applicationExist(String uuid) {
        return applicationRepository.applicationExist(uuid);
    }


    @Override
    public String queryFile(String token, String type) {
        ApplicationE applicationE = applicationRepository.queryByToken(token);
        if (applicationE == null) {
            throw new CommonException("error.app.query.by.token");
        }
        ProjectE projectE = iamRepository.queryIamProject(applicationE.getProjectE().getId());
        Organization organization = iamRepository.queryOrganizationById(projectE.getOrganization().getId());
        InputStream inputStream;
        if (type == null) {
            inputStream = this.getClass().getResourceAsStream("/shell/ci.sh");
        } else {
            inputStream = this.getClass().getResourceAsStream("/shell/" + type + ".sh");
        }
        Map<String, String> params = new HashMap<>();
        params.put("{{ GROUP_NAME }}", organization.getCode() + "-" + projectE.getCode());
        params.put("{{ PROJECT_NAME }}", applicationE.getCode());
        return FileUtil.replaceReturnString(inputStream, params);
    }

    @Override
    public List<ApplicationCodeDTO> listByEnvId(Long projectId, Long envId, String status) {
        return ConvertHelper.convertList(applicationRepository.listByEnvId(projectId, envId, status),
                ApplicationCodeDTO.class);
    }

    @Override
    public Page<ApplicationCodeDTO> pageByEnvId(Long projectId, Long envId, PageRequest pageRequest) {
        return ConvertPageHelper.convertPage(applicationRepository.pageByEnvId(projectId, envId, pageRequest),
                ApplicationCodeDTO.class);
    }

    @Override
    public Page<ApplicationDTO> listByActiveAndPubAndVersion(Long projectId, PageRequest pageRequest, String params) {
        return ConvertPageHelper.convertPage(applicationRepository
                        .listByActiveAndPubAndVersion(projectId, true, pageRequest, params),
                ApplicationDTO.class);
    }

}
