package io.choerodon.devops.infra.dingtalk.api;

import feign.Headers;
import feign.RequestLine;
import io.choerodon.devops.infra.dingtalk.ApiClient;
import io.choerodon.devops.infra.dingtalk.model.Message;
import io.choerodon.devops.infra.dingtalk.model.MessageResult;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-14T17:49:16.234+08:00")
public interface MessageApi extends ApiClient.Api {


  /**
   * 发送企业会话消息
   * 企业可以主动发消息给员工。  发送企业会话消息和发送普通会话消息的不同之处在于发送消息的主体不同  * 普通会话消息发送主体是普通员工，体现在接收方手机上的联系人是消息发送员工 * 企业会话消息发送主体是企业，体现在接收方手机上的联系人是你填写的agentid对应的微应用  注意：不管是企业接入方式，还是ISV接入方式，调用发消息的接口不应过于频繁和超量，否则会被限制消息的推送。  调用接口时，使用Https协议、JSON数据包格式。  目前支持text、image、voice、file、link、OA消息类型。每个消息都由消息头和消息体组成，企业会话的消息头由touser,toparty,agentid组成。 
   * @param data 消息体 (optional)
   * @return MessageResult
   */
  @RequestLine("POST /message/send")
  @Headers({
    "Content-type: application/json",
    "Accept: application/json",
  })
  MessageResult send(Message data);
}