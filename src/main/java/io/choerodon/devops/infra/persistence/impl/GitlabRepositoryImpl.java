package io.choerodon.devops.infra.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.domain.application.entity.gitlab.GitlabGroupE;
import io.choerodon.devops.domain.application.repository.GitlabRepository;
import io.choerodon.devops.domain.application.valueobject.ProjectHook;
import io.choerodon.devops.domain.application.valueobject.RepositoryFile;
import io.choerodon.devops.domain.application.valueobject.Variable;
import io.choerodon.devops.infra.common.util.GitUtil;
import io.choerodon.devops.infra.dataobject.gitlab.GitlabProjectDO;
import io.choerodon.devops.infra.dataobject.gitlab.GroupDO;
import io.choerodon.devops.infra.dataobject.gitlab.ImpersonationTokenDO;
import io.choerodon.devops.infra.feign.GitlabServiceClient;

/**
 * Created by younger on 2018/3/29.
 */
@Component
public class GitlabRepositoryImpl implements GitlabRepository {

    private GitlabServiceClient gitlabServiceClient;
    private GitUtil gitUtil;

    public GitlabRepositoryImpl(GitlabServiceClient gitlabServiceClient, GitUtil gitUtil) {
        this.gitlabServiceClient = gitlabServiceClient;
        this.gitUtil = gitUtil;
    }

    @Override
    public void addVariable(Integer gitlabProjectId, String key, String value, Boolean protecteds, Integer userId) {
        gitlabServiceClient.addVariable(gitlabProjectId, key, value, protecteds, userId);
    }

    @Override
    public List<String> listTokenByUserId(Integer gitlabProjectId, String name, Integer userId) {
        ResponseEntity<List<ImpersonationTokenDO>> impersonationTokens;
        try {
            impersonationTokens = gitlabServiceClient
                    .listTokenByUserId(userId);
        } catch (FeignException e) {
            gitUtil.deleteWorkingDirectory(name);
            gitlabServiceClient.deleteProject(gitlabProjectId, userId);
            throw new CommonException(e);
        }
        List<String> tokens = new ArrayList<>();
        impersonationTokens.getBody().parallelStream().forEach(impersonationToken ->
                tokens.add(impersonationToken.getToken())
        );
        return tokens;
    }

    @Override
    public String createToken(Integer gitlabProjectId, String name, Integer userId) {
        ResponseEntity<ImpersonationTokenDO> impersonationToken;
        try {
            impersonationToken = gitlabServiceClient.createToken(userId);
        } catch (FeignException e) {
            gitUtil.deleteWorkingDirectory(name);
            gitlabServiceClient.deleteProject(gitlabProjectId, userId);
            throw new CommonException(e);
        }
        return impersonationToken.getBody().getToken();
    }

    @Override
    public GitlabGroupE queryGroupByName(String groupName, Integer userId) {
        ResponseEntity<GroupDO> groupDO;
        try {
            groupDO = gitlabServiceClient.queryGroupByName(groupName, userId);
        } catch (FeignException e) {
            throw new CommonException(e);
        }
        if (groupDO != null) {
            return ConvertHelper.convert(groupDO.getBody(), GitlabGroupE.class);
        } else {
            return null;
        }
    }

    @Override
    public GitlabGroupE createGroup(GitlabGroupE gitlabGroupE, Integer userId) {
        ResponseEntity<GroupDO> groupDO;
        try {
            groupDO = gitlabServiceClient.createGroup(ConvertHelper.convert(
                    gitlabGroupE, GroupDO.class), userId);
        } catch (FeignException e) {
            throw new CommonException(e);
        }
        return ConvertHelper.convert(groupDO.getBody(), GitlabGroupE.class);
    }

    @Override
    public void createFile(Integer projectId, String path, String content, String commitMessage, Integer userId) {
        ResponseEntity<RepositoryFile> result = gitlabServiceClient.createFile(projectId, path, content, commitMessage, userId);
        if (result.getBody().getFilePath() == null) {
            throw new CommonException("error.file.create");
        }
    }

    @Override
    public void updateFile(Integer projectId, String path, String content, String commitMessage, Integer userId) {
        ResponseEntity<RepositoryFile> result = gitlabServiceClient.updateFile(projectId, path, content, commitMessage, userId);
        if (result.getBody().getFilePath() == null) {
            throw new CommonException("error.file.update");
        }
    }

    @Override
    public void deleteFile(Integer projectId, String path, String commitMessage, Integer userId) {
        try {
            gitlabServiceClient.deleteFile(projectId, path, commitMessage, userId);
        } catch (FeignException e) {
            throw new CommonException("error.file.delete", e);
        }
    }

    @Override
    public void deleteDevOpsApp(String groupName, String projectName, Integer userId) {
        try {
            gitlabServiceClient.deleteProjectByProjectName(groupName, projectName, userId);
        } catch (FeignException e) {
            throw new CommonException("error.app.delete", e);
        }
    }

    @Override
    public Boolean getFile(Integer projectId, String branch, String filePath) {
        return gitlabServiceClient.getFile(projectId, branch, filePath).getBody().getFilePath() == null ? false : true;
    }

    @Override
    public void createProtectBranch(Integer projectId, String name, String mergeAccessLevel, String pushAccessLevel, Integer userId) {
        try {
            gitlabServiceClient.createProtectedBranches(
                    projectId, name, mergeAccessLevel, pushAccessLevel, userId);
        } catch (FeignException e) {
            throw new CommonException("error.branch.create", e);
        }
    }

    @Override
    public void deleteProject(Integer projectId, Integer userId) {
        try {
            gitlabServiceClient.deleteProject(projectId, userId);

        } catch (FeignException e) {
            throw new CommonException(e);
        }
    }


    @Override
    public String updateProject(Integer projectId, Integer userId) {
        try {
            return gitlabServiceClient.updateProject(projectId, userId).getBody().getDefaultBranch();
        } catch (FeignException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public ProjectHook createWebHook(Integer projectId, Integer userId, ProjectHook projectHook) {
        try {
            return gitlabServiceClient
                    .createProjectHook(projectId, userId, projectHook).getBody();
        } catch (FeignException e) {
            throw new CommonException("error.projecthook.create", e);

        }
    }


    @Override
    public ProjectHook updateWebHook(Integer projectId, Integer hookId, Integer userId) {
        ResponseEntity<ProjectHook> projectHookResponseEntity;
        try {
            projectHookResponseEntity = gitlabServiceClient
                    .updateProjectHook(projectId, hookId, userId);
        } catch (FeignException e) {
            throw new CommonException(e.getMessage(), e);
        }
        return projectHookResponseEntity.getBody();
    }

    @Override
    public GitlabProjectDO createProject(Integer groupId, String projectName, Integer userId, boolean visibility) {
        try {
            return gitlabServiceClient
                    .createProject(groupId, projectName, userId, visibility).getBody();
        } catch (FeignException e) {
            throw new CommonException("error.gitlab.project.create", e);

        }
    }

    @Override
    public GitlabProjectDO getProjectByName(String groupName, String projectName, Integer userId) {
        try {
            return gitlabServiceClient.getProjectByName(userId, groupName, projectName).getBody();
        } catch (FeignException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public List<ProjectHook> getHooks(Integer projectId, Integer userId) {
        try {
            return gitlabServiceClient.getProjectHook(projectId, userId).getBody();
        } catch (FeignException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public List<Variable> getVariable(Integer projectId, Integer userId) {
        try {
            return gitlabServiceClient.getVariable(projectId, userId).getBody();
        } catch (FeignException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public void createDeployKey(Integer projectId, String title, String key, boolean canPush, Integer userId) {
        try {
            gitlabServiceClient.createDeploykey(projectId, title, key, canPush, userId);
        } catch (FeignException e) {
            throw new CommonException("error.deploykey.create", e);
        }
    }
}
