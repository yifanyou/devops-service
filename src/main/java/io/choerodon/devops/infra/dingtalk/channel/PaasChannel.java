package io.choerodon.devops.infra.dingtalk.channel;

import io.choerodon.devops.infra.dingtalk.api.RobotApi;
import io.choerodon.devops.infra.dingtalk.builder.RobotMessageBuilder;
import io.choerodon.devops.infra.dingtalk.model.RobotMessage;

public class PaasChannel implements RobotChannel{

    private RobotApi robotApi;

    private String paasToken;

    public PaasChannel(RobotApi robotApi, String paasToken) {
        this.robotApi = robotApi;
        this.paasToken = paasToken;
    }

    @Override
    public void sendMessage(String title, String message, boolean isAll) {
        RobotMessage robotMessage = RobotMessageBuilder.newMarkdownMessage()
                .isAtAll(isAll)
                .title(title)
                .text(message)
                .build();
        robotApi.send(paasToken, robotMessage);
    }
}
