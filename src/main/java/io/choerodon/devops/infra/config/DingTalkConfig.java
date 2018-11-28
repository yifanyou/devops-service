package io.choerodon.devops.infra.config;

import io.choerodon.devops.infra.dingtalk.ApiClient;
import io.choerodon.devops.infra.dingtalk.DingTalkSettings;
import io.choerodon.devops.infra.dingtalk.api.RobotApi;
import io.choerodon.devops.infra.dingtalk.channel.PaasChannel;
import io.choerodon.devops.infra.dingtalk.channel.RobotChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DingTalkSettings.class)
public class DingTalkConfig {

    @Bean
    public RobotApi robotApi() {
        //https://oapi.dingtalk.com/robot/send?access_token=c400dab4e47d910ea7f075988199b7e7402717286d27ee9e7328ed15ff0e694b
        ApiClient apiClient = new ApiClient();
        return apiClient.buildClient(RobotApi.class);
    }

    @Bean("PaasChannel")
    public RobotChannel paasChannel(RobotApi robotApi, @Value("${xplat.social.dingtalk.paasToken}") String paasToken){
        return new PaasChannel(robotApi, paasToken);
    }
}