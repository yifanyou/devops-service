package io.choerodon.devops.infra.dingtalk.channel;

import io.choerodon.devops.infra.dingtalk.domain.MarkDownDomain;

public interface RobotChannel {

    void sendMessage(String title, String message, boolean isAll);

    default void sendMessage(String title, MarkDownDomain markDownDomain, boolean isAll){
        sendMessage(title, markDownDomain, isAll);
    }

    default void sendMessageToAll(String title, MarkDownDomain markDownDomain){
        sendMessage(title, markDownDomain, true);
    }

    default void sendMessageToAll(String title, String message){
        sendMessage(title, message, true);
    }
}