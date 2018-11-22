package io.choerodon.devops.domain.application.repository;

import io.choerodon.core.domain.Page;
import io.choerodon.devops.domain.application.entity.ApplicationE;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;

import java.util.List;

/**
 * Created by younger on 2018/3/28.
 */
public interface ApplicationRepositoryEx {

    Page<ApplicationE> listByOptionsAndProjectIds(List<Long> projectIds,
                                     Boolean isActive,
                                     Boolean hasVersion,
                                     PageRequest pageRequest,
                                     String params);
}
