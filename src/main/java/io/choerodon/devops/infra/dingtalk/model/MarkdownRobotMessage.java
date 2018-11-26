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

import java.util.Objects;


/**
 * markdown消息
 */
@ApiModel(description = "markdown消息")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-14T17:49:16.234+08:00")
public class MarkdownRobotMessage   {
  @JsonProperty("markdown")
  private Markdown markdown = null;

  public MarkdownRobotMessage markdown(Markdown markdown) {
    this.markdown = markdown;
    return this;
  }

   /**
   * Get markdown
   * @return markdown
  **/
  @ApiModelProperty(example = "null", value = "")
  public Markdown getMarkdown() {
    return markdown;
  }

  public void setMarkdown(Markdown markdown) {
    this.markdown = markdown;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MarkdownRobotMessage markdownRobotMessage = (MarkdownRobotMessage) o;
    return Objects.equals(this.markdown, markdownRobotMessage.markdown);
  }

  @Override
  public int hashCode() {
    return Objects.hash(markdown);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MarkdownRobotMessage {\n");

    sb.append("    markdown: ").append(toIndentedString(markdown)).append("\n");
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
