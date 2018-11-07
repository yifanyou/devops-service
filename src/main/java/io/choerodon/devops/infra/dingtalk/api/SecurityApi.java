package io.choerodon.devops.infra.dingtalk.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.choerodon.devops.infra.dingtalk.ApiClient;
import io.choerodon.devops.infra.dingtalk.model.AccessTokenResult;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaClientCodegen", date = "2017-07-14T17:49:16.234+08:00")
public interface SecurityApi extends ApiClient.Api {


  /**
   * 获取AccessToken
   * AccessToken是企业访问钉钉开放平台接口的全局唯一票据，调用接口时需携带AccessToken。  AccessToken需要用CorpID和CorpSecret来换取，不同的CorpSecret会返回不同的AccessToken。正常情况下AccessToken有效期为7200秒，有效期内重复获取返回相同结果，并自动续期。 
   * @param corpid 企业Id (required)
   * @param corpsecret 企业应用的凭证密钥 (required)
   * @return AccessTokenResult
   */
  @RequestLine("GET /gettoken?corpid={corpid}&corpsecret={corpsecret}")
  @Headers({
    "Content-type: application/json",
    "Accept: application/json",
  })
  AccessTokenResult getToken(@Param("corpid") String corpid, @Param("corpsecret") String corpsecret);
}
