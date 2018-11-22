package io.choerodon.devops.app.service;

import io.choerodon.core.domain.Page;
import io.choerodon.devops.api.dto.ApplicationRepDTO;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

public interface ApplicationServiceEx {

    /**
     * 组织下分页查询应用
     *
     * @param orgId   项目id
     * @param isActive    是否启用
     * @param hasVersion  是否存在版本
     * @param pageRequest 分页参数
     * @param params      参数
     * @return Page
     */
    Page<ApplicationRepDTO> listByOptionsInOrg(Long orgId,
                                          Boolean isActive,
                                          Boolean hasVersion,
                                          PageRequest pageRequest,
                                          String params);
}
