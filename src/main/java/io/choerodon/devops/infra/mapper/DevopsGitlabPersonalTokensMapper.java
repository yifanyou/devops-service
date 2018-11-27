package io.choerodon.devops.infra.mapper;

import io.choerodon.devops.infra.dataobject.DevopsGitlabPersonalTokensDO;
import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevopsGitlabPersonalTokensMapper extends BaseMapper<DevopsGitlabPersonalTokensDO> {

    List<DevopsGitlabPersonalTokensDO> queryByGitlabUserId(@Param("gitlabUserId") Integer userId);

}
