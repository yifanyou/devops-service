package io.choerodon.devops.infra.persistence.impl;

import org.springframework.stereotype.Component;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.devops.domain.application.entity.UserAttrE;
import io.choerodon.devops.domain.application.repository.UserAttrRepository;
import io.choerodon.devops.infra.dataobject.UserAttrDO;
import io.choerodon.devops.infra.mapper.UserAttrMapper;

/**
 * Created by Zenger on 2018/3/28.
 */
@Component
public class UserAttrRepositoryImpl implements UserAttrRepository {

    private UserAttrMapper userAttrMapper;

    public UserAttrRepositoryImpl(UserAttrMapper userAttrMapper) {
        this.userAttrMapper = userAttrMapper;
    }

    @Override
    public int insert(UserAttrE userAttrE) {
        return userAttrMapper.insert(ConvertHelper.convert(userAttrE, UserAttrDO.class));
    }

    @Override
    public UserAttrE queryById(Long id) {
        return ConvertHelper.convert(userAttrMapper.selectByPrimaryKey(id), UserAttrE.class);
    }

    @Override
    public Long queryUserIdByGitlabUserId(Long gitLabUserId) {
        UserAttrDO userAttrDO = new UserAttrDO();
        userAttrDO.setGitlabUserId(gitLabUserId);
        if (gitLabUserId == null) {
            return null;
        }
        userAttrDO = userAttrMapper.selectOne(userAttrDO);
        if(userAttrDO==null) {
            return null;
        }else {
            return userAttrDO.getId();
        }
    }

}
