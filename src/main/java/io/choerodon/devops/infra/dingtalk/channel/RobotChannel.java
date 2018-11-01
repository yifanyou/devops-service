package io.choerodon.devops.infra.dingtalk.channel;

public interface RobotChannel {

    void sendMessage(String title, String message, boolean isAll);

    default void sendMessageToAll(String title, String message){
        sendMessage(title, message, true);
    }

}