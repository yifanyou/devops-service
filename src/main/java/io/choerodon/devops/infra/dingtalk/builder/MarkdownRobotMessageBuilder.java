package io.choerodon.devops.infra.dingtalk.builder;


import io.choerodon.devops.infra.dingtalk.model.Markdown;
import io.choerodon.devops.infra.dingtalk.model.RobotMessage;

/**
 * Created by fanguozhu on 2017/7/14.
 */
public class MarkdownRobotMessageBuilder extends RobotMessageBuilder<MarkdownRobotMessageBuilder> {
    private String text;
    private String title;

    public MarkdownRobotMessageBuilder text(String text) {
        this.text = text;
        return this;
    }

    public MarkdownRobotMessageBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public RobotMessage build() {
        RobotMessage message = new RobotMessage()
                .markdown(new Markdown()
                        .text(text)
                        .title(title));
        build(message);
//        message.setMsgtype(RobotMessage.MsgtypeEnum.MARKDOWN);
        return message;
    }
}
