package io.choerodon.devops.infra.dingtalk.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.choerodon.devops.infra.dingtalk.ApiClient;
import io.choerodon.devops.infra.dingtalk.model.MessageResult;
import io.choerodon.devops.infra.dingtalk.model.RobotMessage;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-14T17:49:16.234+08:00")
public interface RobotApi extends ApiClient.Api {


  /**
   * 发送机器人消息
   * 获取到Webhook地址后，用户可以使用任何方式向这个地址发起HTTP POST 请求，即可实现给该群组发送消息。注意，发起POST请求时，必须将字符集编码设置成UTF-8。  当前自定义机器人支持文本（text）、连接（link）、markdown（markdown）三种消息类型，大家可以根据自己的使用场景选择合适的消息类型，达到最好的展示样式。具体的消息类型参考下一节内容。  自定义机器人发送消息时，可以通过手机号码指定“被@人列表”。在“被@人列表”里面的人员，在收到该消息时，会有@消息提醒（免打扰会话仍然通知提醒，首屏出现“有人@你”） 
   * @param accessToken 机器人对应的Webhook (optional)
   * @param message 消息体 (optional)
   * @return MessageResult
   */
  @RequestLine("POST /robot/send?access_token={accessToken}")
  @Headers({
    "Content-type: application/json",
    "Accept: application/json",
  })
  MessageResult send(@Param("accessToken") String accessToken, RobotMessage message);
}
