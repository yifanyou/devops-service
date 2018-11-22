package io.choerodon.devops.app.service.impl;

import io.choerodon.core.convertor.ConvertPageHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.devops.api.dto.ApplicationRepDTO;
import io.choerodon.devops.app.service.ApplicationServiceEx;
import io.choerodon.devops.domain.application.entity.ApplicationE;
import io.choerodon.devops.domain.application.entity.ProjectE;
import io.choerodon.devops.domain.application.repository.ApplicationRepository;
import io.choerodon.devops.domain.application.repository.ApplicationRepositoryEx;
import io.choerodon.devops.domain.application.repository.IamRepository;
import io.choerodon.devops.domain.application.valueobject.Organization;
import io.choerodon.devops.infra.common.util.HttpClientUtil;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceExImpl implements ApplicationServiceEx {

    @Autowired
    private ApplicationRepositoryEx applicationRepositoryEx;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private IamRepository iamRepository;

    @Value("${services.gitlab.url}")
    private String gitlabUrl;

    @Value("${services.sonarqube.url}")
    private String sonarqubeUrl;

    @Override
    public Page<ApplicationRepDTO> listByOptionsInOrg(Long orgId, Boolean isActive, Boolean hasVersion, PageRequest pageRequest, String params) {

        List<ProjectE> projectEList = iamRepository.listIamProjectByOrgId(orgId);
        Map<Long, ProjectE> projectMapping = projectEList.stream().collect(Collectors.toMap(ProjectE::getId, x -> x));
        Page<ApplicationE> applicationES =
                applicationRepositoryEx.listByOptionsAndProjectIds(projectEList.stream()
                        .map(ProjectE::getId).collect(Collectors.toList()), isActive, hasVersion, pageRequest, params);
        Organization organization = iamRepository.queryOrganizationById(orgId);
        String urlSlash = gitlabUrl.endsWith("/") ? "" : "/";
        applicationES.getContent().parallelStream()
                .forEach(t -> {
                    ProjectE projectE = projectMapping.get(t.getProjectE().getId());
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
}
