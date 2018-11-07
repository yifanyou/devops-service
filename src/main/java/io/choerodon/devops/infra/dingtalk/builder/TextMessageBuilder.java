package io.choerodon.devops.infra.dingtalk.builder;

import io.choerodon.devops.infra.dingtalk.model.Message;
import io.choerodon.devops.infra.dingtalk.model.Text;
import io.choerodon.devops.infra.dingtalk.model.TextMessage;

/**
 * Created by fanguozhu on 16/12/11.
 */
public class TextMessageBuilder extends MessageBuilder<TextMessageBuilder> {

    private Text text;

    @Override
    public Message build() {
        TextMessage message = new TextMessage().text(text);
        build(message);
        message.setMsgtype(Message.MsgtypeEnum.TEXT);
        return message;
    }

    public TextMessageBuilder text(Text text) {
        this.text = text;
        return this;
    }
}
