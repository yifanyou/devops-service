package io.choerodon.devops.domain.application.repository;

import java.util.List;

public interface DevopsGitlabPersonalTokensRepository {

    List<String> listTokenByUserId(Integer gitlabProjectId, String name, Integer userId);

    String createToken(Integer gitlabProjectId, String name, Integer userId);
}
