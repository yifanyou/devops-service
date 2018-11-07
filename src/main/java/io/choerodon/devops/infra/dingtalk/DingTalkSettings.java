package io.choerodon.devops.infra.dingtalk;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("xplat.social.ding-talk")
public class DingTalkSettings {

    private String corpId = "ding92322adf291d4ebc";

    private String corpSecret = "_Z24C2zszsd70iOInwgaye1yi4yRA3iGwxEazO1S725GvKbhOQ0jMskQhQFirFZv";

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }
}