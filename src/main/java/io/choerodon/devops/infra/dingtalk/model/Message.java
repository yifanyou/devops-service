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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;


/**
 * 基本消息体
 */
@ApiModel(description = "基本消息体")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-14T17:49:16.234+08:00")
public class Message   {
  @JsonProperty("touser")
  private String touser = null;

  @JsonProperty("toparty")
  private String toparty = null;

  @JsonProperty("agentid")
  private String agentid = null;

  /**
   * 消息类型
   */
  public enum MsgtypeEnum {
    TEXT("text"),
    
    IMAGE("image"),
    
    LINK("link"),
    
    OA("oa");

    private String value;

    MsgtypeEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static MsgtypeEnum fromValue(String text) {
      for (MsgtypeEnum b : MsgtypeEnum.values()) {
          if (String.valueOf(b.value).equals(text)) {
              return b;
          }
      }
      return null;
    }
  }

  @JsonProperty("msgtype")
  private MsgtypeEnum msgtype = null;

  public Message touser(String touser) {
    this.touser = touser;
    return this;
  }

   /**
   * 员工id列表（消息接收者，多个接收者用|分隔）
   * @return touser
  **/
  @ApiModelProperty(example = "null", value = "员工id列表（消息接收者，多个接收者用|分隔）")
  public String getTouser() {
    return touser;
  }

  public void setTouser(String touser) {
    this.touser = touser;
  }

  public Message toparty(String toparty) {
    this.toparty = toparty;
    return this;
  }

   /**
   * 部门id列表，多个接收者用|分隔。touser或者toparty 二者有一个必填
   * @return toparty
  **/
  @ApiModelProperty(example = "null", value = "部门id列表，多个接收者用|分隔。touser或者toparty 二者有一个必填")
  public String getToparty() {
    return toparty;
  }

  public void setToparty(String toparty) {
    this.toparty = toparty;
  }

  public Message agentid(String agentid) {
    this.agentid = agentid;
    return this;
  }

   /**
   * 企业应用id，这个值代表以哪个应用的名义发送消息
   * @return agentid
  **/
  @ApiModelProperty(example = "null", value = "企业应用id，这个值代表以哪个应用的名义发送消息")
  public String getAgentid() {
    return agentid;
  }

  public void setAgentid(String agentid) {
    this.agentid = agentid;
  }

  public Message msgtype(MsgtypeEnum msgtype) {
    this.msgtype = msgtype;
    return this;
  }

   /**
   * 消息类型
   * @return msgtype
  **/
  @ApiModelProperty(example = "null", value = "消息类型")
  public MsgtypeEnum getMsgtype() {
    return msgtype;
  }

  public void setMsgtype(MsgtypeEnum msgtype) {
    this.msgtype = msgtype;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Message message = (Message) o;
    return Objects.equals(this.touser, message.touser) &&
        Objects.equals(this.toparty, message.toparty) &&
        Objects.equals(this.agentid, message.agentid) &&
        Objects.equals(this.msgtype, message.msgtype);
  }

  @Override
  public int hashCode() {
    return Objects.hash(touser, toparty, agentid, msgtype);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Message {\n");

    sb.append("    touser: ").append(toIndentedString(touser)).append("\n");
    sb.append("    toparty: ").append(toIndentedString(toparty)).append("\n");
    sb.append("    agentid: ").append(toIndentedString(agentid)).append("\n");
    sb.append("    msgtype: ").append(toIndentedString(msgtype)).append("\n");
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

