package io.choerodon.devops.infra.dingtalk.domain;

/**
 *  申请类型：新建/重连
 *  申请组织：组织名称[组织ID][组织CODE]
 *  申请项目：项目名称[项目ID][项目CODE]
 *  申请人：姓名[ID][CODE]
 *  环境名称
 *  环境描述
 *  环境分区
 *  申请时间：申请提交的时间
 *  命令：helm命令
 */
public class EnvApplicationDomain implements MarkDownDomain{

    private String type;

    private String organizationInfo;

    private String projectInfo;

    private String userInfo;

    private String envName;

    private String envDesc;

    private String partition;

    private String applicationTime;

    private String cmd;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrganizationInfo() {
        return organizationInfo;
    }

    public void setOrganizationInfo(String organizationInfo) {
        this.organizationInfo = organizationInfo;
    }

    public String getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getEnvDesc() {
        return envDesc;
    }

    public void setEnvDesc(String envDesc) {
        this.envDesc = envDesc;
    }

    public String getPartition() {
        return partition;
    }

    public void setPartition(String partition) {
        this.partition = partition;
    }

    public String getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(String applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toMarkDown() {
        return String.format(
            "* 申请类型: %s\n\n"+
            "* 申请组织: %s\n\n"+
            "* 申请项目: %s\n\n"+
            "* 申请人: %s\n\n"+
            "* 环境名称: %s\n\n"+
            "* 环境描述: %s\n\n"+
            "* 环境分区: %s\n\n"+
            "* 申请时间: %s\n\n"+
            "* 命令: %s\n"
                , getType()
                , getOrganizationInfo()
                , getProjectInfo()
                , getUserInfo()
                , getEnvName()
                , getEnvDesc()
                , getPartition()
                , getApplicationTime()
                , getCmd()
        );
    }
}
