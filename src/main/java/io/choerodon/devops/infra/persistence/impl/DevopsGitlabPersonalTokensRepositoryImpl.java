package io.choerodon.devops.infra.persistence.impl;

import io.choerodon.devops.domain.application.repository.DevopsGitlabPersonalTokensRepository;
import io.choerodon.devops.domain.application.repository.GitlabRepository;
import io.choerodon.devops.infra.dataobject.DevopsGitlabPersonalTokensDO;
import io.choerodon.devops.infra.mapper.DevopsGitlabPersonalTokensMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DevopsGitlabPersonalTokensRepositoryImpl implements DevopsGitlabPersonalTokensRepository {

    @Autowired
    private GitlabRepository gitlabRepository;

    private DevopsGitlabPersonalTokensMapper mapper;

    public DevopsGitlabPersonalTokensRepositoryImpl(DevopsGitlabPersonalTokensMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public List<String> listTokenByUserId(Integer gitlabProjectId, String name, Integer userId) {
        List<DevopsGitlabPersonalTokensDO> impersonationTokens;
            impersonationTokens = mapper.queryByGitlabUserId(userId);
        return impersonationTokens.stream().map(DevopsGitlabPersonalTokensDO::getToken).collect(Collectors.toList());
    }

    @Override
    public String createToken(Integer gitlabProjectId, String name, Integer userId, Integer iamUserId) {
        String token = gitlabRepository.createToken(gitlabProjectId, name, userId);
        DevopsGitlabPersonalTokensDO devopsGitlabPersonalTokensDO = new DevopsGitlabPersonalTokensDO();
        devopsGitlabPersonalTokensDO.setGitlabUserId(userId);
        devopsGitlabPersonalTokensDO.setToken(token);
        devopsGitlabPersonalTokensDO.setUserId(iamUserId);
        mapper.insert(devopsGitlabPersonalTokensDO);
        return token;
    }
}
