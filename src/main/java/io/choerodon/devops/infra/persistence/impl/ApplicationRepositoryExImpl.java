package io.choerodon.devops.infra.persistence.impl;

import io.choerodon.core.convertor.ConvertPageHelper;
import io.choerodon.core.domain.Page;
import io.choerodon.devops.domain.application.entity.ApplicationE;
import io.choerodon.devops.domain.application.repository.ApplicationRepositoryEx;
import io.choerodon.devops.domain.application.repository.IamRepository;
import io.choerodon.devops.infra.common.util.TypeUtil;
import io.choerodon.devops.infra.dataobject.ApplicationDO;
import io.choerodon.devops.infra.mapper.ApplicationExMapper;
import io.choerodon.devops.infra.mapper.ApplicationMapper;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import io.kubernetes.client.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ApplicationRepositoryExImpl implements ApplicationRepositoryEx {

    private JSON json = new JSON();

    @Autowired
    private IamRepository iamRepository;
    private ApplicationExMapper applicationExMapper;

    public ApplicationRepositoryExImpl(ApplicationExMapper applicationExMapper) {
        this.applicationExMapper = applicationExMapper;
    }

    public String checkSortIsEmpty(PageRequest pageRequest) {
        String index = "";
        if (pageRequest.getSort() == null) {
            index = "true";
        }
        return index;
    }

    @Override
    public Page<ApplicationE> listByOptionsAndProjectIds(List<Long> projectIds, Boolean isActive, Boolean hasVersion, PageRequest pageRequest, String params) {
            Page<ApplicationDO> applicationES;
            if (!StringUtils.isEmpty(params)) {
                Map<String, Object> maps = json.deserialize(params, Map.class);
                if (maps.get(TypeUtil.SEARCH_PARAM).equals("")) {
                    applicationES = PageHelper.doPageAndSort(
                            pageRequest, () -> applicationExMapper.list(
                                    projectIds, isActive, hasVersion, null,
                                    TypeUtil.cast(maps.get(TypeUtil.PARAM))
                                    , checkSortIsEmpty(pageRequest)));
                } else {
                    applicationES = PageHelper.doPageAndSort(
                            pageRequest, () -> applicationExMapper.list(
                                    projectIds, isActive, hasVersion, TypeUtil.cast(maps.get(TypeUtil.SEARCH_PARAM)),
                                    TypeUtil.cast(maps.get(TypeUtil.PARAM)), checkSortIsEmpty(pageRequest)));
                }
            } else {
                applicationES = PageHelper.doPageAndSort(
                        pageRequest, () -> applicationExMapper.list(projectIds, isActive, hasVersion,
                                null, null, checkSortIsEmpty(pageRequest)));
            }
            return ConvertPageHelper.convertPage(applicationES, ApplicationE.class);
    }
}
