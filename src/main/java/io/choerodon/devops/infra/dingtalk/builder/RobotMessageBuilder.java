package io.choerodon.devops.infra.dingtalk.builder;


import io.choerodon.devops.infra.dingtalk.model.MessageAt;
import io.choerodon.devops.infra.dingtalk.model.RobotMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanguozhu on 16/12/11.
 */
public abstract class RobotMessageBuilder<T extends RobotMessageBuilder> {

    private List<String> atMobiles = new ArrayList<String>();
    private Boolean isAtAll;

    public static MarkdownRobotMessageBuilder newMarkdownMessage() {
        return new MarkdownRobotMessageBuilder();
    }

    public T atMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
        return (T) this;
    }

    public T addAtMobilesItem(String atMobilesItem) {
        this.atMobiles.add(atMobilesItem);
        return (T) this;
    }

    public T isAtAll(Boolean isAtAll) {
        this.isAtAll = isAtAll;
        return (T) this;
    }


    public abstract RobotMessage build();

    protected void build(RobotMessage message) {
        message.setAt(new MessageAt().atMobiles(atMobiles).isAtAll(isAtAll));
    }

}
