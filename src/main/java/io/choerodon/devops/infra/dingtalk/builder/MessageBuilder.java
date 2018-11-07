package io.choerodon.devops.infra.dingtalk.builder;


import io.choerodon.devops.infra.dingtalk.model.Message;

/**
 * Created by fanguozhu on 16/12/11.
 */
public abstract class MessageBuilder<T extends MessageBuilder> {

    protected String toUser;
    protected String toParty;
    protected String agentId;

    public static OaMessageBuilder newOaMessage() {
        return new OaMessageBuilder();
    }

    public static TextMessageBuilder newTextMessage() {
        return new TextMessageBuilder();
    }

    public static LinkMessageBuilder newLinkMessage() {
        return new LinkMessageBuilder();
    }

    public T toUser(String toUser) {
        this.toUser = toUser;
        return (T) this;
    }

    public T toParty(String toParty) {
        this.toParty = toParty;
        return (T) this;
    }

    public T agentId(String agentId) {
        this.agentId = agentId;
        return (T) this;
    }


    public abstract Message build();

    protected void build(Message message) {
        message.setTouser(toUser);
        message.setToparty(toParty);
        message.setAgentid(agentId);
    }

}
