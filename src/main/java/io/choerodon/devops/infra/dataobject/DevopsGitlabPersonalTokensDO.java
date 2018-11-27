package io.choerodon.devops.infra.dataobject;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "devops_gitlab_personal_tokens")
public class DevopsGitlabPersonalTokensDO {

    @Id
    @GeneratedValue
    private Long id;

    private Integer gitlabUserId;

    private Integer userId;

    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGitlabUserId() {
        return gitlabUserId;
    }

    public void setGitlabUserId(Integer gitlabUserId) {
        this.gitlabUserId = gitlabUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
