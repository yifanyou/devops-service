package io.choerodon.devops.infra.dingtalk.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.choerodon.devops.infra.dingtalk.ApiClient;
import io.choerodon.devops.infra.dingtalk.model.DepartmentDetail;
import io.choerodon.devops.infra.dingtalk.model.DepartmentListResult;
import io.choerodon.devops.infra.dingtalk.model.UserListResult;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-14T17:49:16.234+08:00")
public interface ContactsApi extends ApiClient.Api {


  /**
   * 获取部门详情
   * 
   * @param id 父部门id (required)
   * @param lang 通讯录语言(默认zh_CN另外支持en_US) (optional, default to zh_CN)
   * @return DepartmentDetail
   */
  @RequestLine("GET /department/get?id={id}&lang={lang}")
  @Headers({
    "Content-type: application/json",
    "Accept: application/json",
  })
  DepartmentDetail getDepartmentDetails(@Param("id") Long id, @Param("lang") String lang);

  /**
   * 获取部门列表
   * 
   * @param id 父部门id (required)
   * @param lang 通讯录语言(默认zh_CN另外支持en_US) (optional, default to zh_CN)
   * @return DepartmentListResult
   */
  @RequestLine("GET /department/list?id={id}&lang={lang}")
  @Headers({
    "Content-type: application/json",
    "Accept: application/json",
  })
  DepartmentListResult getDepartmentList(@Param("id") Long id, @Param("lang") String lang);

  /**
   * 获取部门成员（详情）
   * 
   * @param departmentId 获取的部门id (required)
   * @param lang 通讯录语言(默认zh_CN另外支持en_US) (optional, default to zh_CN)
   * @param offset 支持分页查询，与size参数同时设置时才生效，此参数代表偏移量 (optional, default to 0)
   * @param size 支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，最大100 (optional, default to 100)
   * @param order 支持分页查询，部门成员的排序规则，默认不传是按自定义排序；  entry_asc代表按照进入部门的时间升序，  entry_desc代表按照进入部门的时间降序，  modify_asc代表按照部门信息修改时间升序，  modify_desc代表按照部门信息修改时间降序，  custom代表用户定义(未定义时按照拼音)排序  (optional, default to custom)
   * @return UserListResult
   */
  @RequestLine("GET /user/list?department_id={departmentId}&lang={lang}&offset={offset}&size={size}&order={order}")
  @Headers({
    "Content-type: application/json",
    "Accept: application/json",
  })
  UserListResult getUserList(@Param("departmentId") Long departmentId, @Param("lang") String lang, @Param("offset") Long offset, @Param("size") Integer size, @Param("order") String order);
}
