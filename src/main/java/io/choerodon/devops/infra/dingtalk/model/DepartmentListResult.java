/**
 * 钉钉API
 * No descripton provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.choerodon.devops.infra.dingtalk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 部门列表返回结果
 */
@ApiModel(description = "部门列表返回结果")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-14T17:49:16.234+08:00")
public class DepartmentListResult extends Result  {
  @JsonProperty("department")
  private List<Department> department = new ArrayList<Department>();

  public DepartmentListResult department(List<Department> department) {
    this.department = department;
    return this;
  }

  public DepartmentListResult addDepartmentItem(Department departmentItem) {
    this.department.add(departmentItem);
    return this;
  }

   /**
   * 部门列表数据。以部门的order字段从小到大排列
   * @return department
  **/
  @ApiModelProperty(example = "null", value = "部门列表数据。以部门的order字段从小到大排列")
  public List<Department> getDepartment() {
    return department;
  }

  public void setDepartment(List<Department> department) {
    this.department = department;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DepartmentListResult departmentListResult = (DepartmentListResult) o;
    return Objects.equals(this.department, departmentListResult.department) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(department, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DepartmentListResult {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    department: ").append(toIndentedString(department)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
