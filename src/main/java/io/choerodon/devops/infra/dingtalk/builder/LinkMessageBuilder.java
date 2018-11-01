package io.choerodon.devops.infra.dingtalk.builder;

import io.choerodon.devops.infra.dingtalk.model.Link;
import io.choerodon.devops.infra.dingtalk.model.LinkMessage;
import io.choerodon.devops.infra.dingtalk.model.Message;

/**
 * Created by fanguozhu on 16/12/11.
 */
public class LinkMessageBuilder extends MessageBuilder<LinkMessageBuilder> {

    private String messageUrl;
    private String picUrl;
    private String text;
    private String title;

    public LinkMessageBuilder messageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
        return this;
    }

    public LinkMessageBuilder picUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public LinkMessageBuilder text(String text) {
        this.text = text;
        return this;
    }

    public LinkMessageBuilder title(String title) {
        this.title = title;
        return this;
    }

    @Override
    public Message build() {
        LinkMessage message = new LinkMessage()
                .link(new Link()
                        .messageUrl(messageUrl)
                        .picUrl(picUrl)
                        .text(text)
                        .title(title));
        build(message);
        message.setMsgtype(Message.MsgtypeEnum.LINK);
        return message;
    }
}
