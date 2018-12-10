package io.choerodon.devops.infra.mapper;

import io.choerodon.devops.infra.dataobject.ApplicationDO;
import io.choerodon.mybatis.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by younger on 2018/3/28.
 */
public interface ApplicationExMapper extends BaseMapper<ApplicationDO> {

    List<ApplicationDO> list(@Param("projectIds") List<Long> projectIds,
                             @Param("isActive") Boolean isActive,
                             @Param("hasVersion") Boolean hasVersion,
                             @Param("searchParam") Map<String, Object> searchParam,
                             @Param("param") String param,
                             @Param("index") String index);

}
