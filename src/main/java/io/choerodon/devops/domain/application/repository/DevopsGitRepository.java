package io.choerodon.devops.domain.application.repository;


import java.util.List;

import io.choerodon.devops.domain.application.entity.DevopsBranchE;
import io.choerodon.devops.infra.dataobject.gitlab.BranchDO;
import io.choerodon.devops.infra.dataobject.gitlab.TagDO;
import io.choerodon.devops.infra.dataobject.gitlab.TagsDO;

/**
 * Creator: Runge
 * Date: 2018/7/2
 * Time: 14:03
 * Description:
 */
public interface DevopsGitRepository {

    void createTag(Integer gitLabProjectId, String tag, String ref, Integer userId);

    Integer getGitLabId(Long applicationId);

    Integer getGitlabUserId();

    void createDevopsBranch(DevopsBranchE devopsBranchE);

    BranchDO createBranch(Integer projectId, String branchName, String baseBranch, Integer userId);

    List<BranchDO> listBranches(Integer projectId, String path, Integer userId);

    void deleteBranch(Integer projectId, String branchName, Integer userId);

    TagsDO getTags(Long serviceId, String path, Integer page, Integer size, Integer userId);

    List<TagDO> getTagList(Long appId, Integer userId);

    List<TagDO> getGitLabTags(Integer projectId, Integer userId);

    DevopsBranchE queryByAppAndBranchName(Long appId, String branchName);

    void updateBranch(Long appId, DevopsBranchE devopsBranchE);
}