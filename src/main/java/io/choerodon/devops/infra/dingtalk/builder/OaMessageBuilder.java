package io.choerodon.devops.infra.dingtalk.builder;


import io.choerodon.devops.infra.dingtalk.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanguozhu on 16/12/11.
 */
public class OaMessageBuilder extends MessageBuilder<OaMessageBuilder> {

    private String headText;
    private String headBgColor;
    private String bodyTitle;
    private List<FormItem> bodyItems;
    private String content;
    private String author;
    private String fileCount;
    private String image;
    private String messageUrl;
    private String pcMessageUrl;
    private String richNum;
    private String richUnit;

    public OaMessageBuilder headText(String headText) {
        this.headText = headText;
        return this;
    }

    public OaMessageBuilder headBgColor(String headBgColor) {
        this.headBgColor = headBgColor;
        return this;
    }

    public OaMessageBuilder bodyTitle(String bodyTitle) {
        this.bodyTitle = bodyTitle;
        return this;
    }

    public OaMessageBuilder bodyItems(List<FormItem> bodyItems) {
        this.bodyItems = bodyItems;
        return this;
    }

    public OaMessageBuilder bodyItem(String key, String value) {
        if (this.bodyItems == null) {
            this.bodyItems = new ArrayList<>();
        }
        this.bodyItems.add(new FormItem().key(key).value(value));
        return this;
    }

    public OaMessageBuilder content(String content) {
        this.content = content;
        return this;
    }

    public OaMessageBuilder author(String author) {
        this.author = author;
        return this;
    }

    public OaMessageBuilder fileCount(String fileCount) {
        this.fileCount = fileCount;
        return this;
    }

    public OaMessageBuilder image(String image) {
        this.image = image;
        return this;
    }

    public OaMessageBuilder messageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
        return this;
    }

    public OaMessageBuilder pcMessageUrl(String pcMessageUrl) {
        this.pcMessageUrl = pcMessageUrl;
        return this;
    }

    public OaMessageBuilder richNum(String richNum) {
        this.richNum = richNum;
        return this;
    }

    public OaMessageBuilder richUnit(String richUnit) {
        this.richUnit = richUnit;
        return this;
    }

    @Override
    public Message build() {
        OaMessage message = new OaMessage();
        build(message);
        message.setMsgtype(Message.MsgtypeEnum.OA);

        Oa oa = new Oa();
        if (headText != null || headBgColor != null) {
            OaHead head = new OaHead();
            head.setText(headText);
            head.setBgcolor(headBgColor);
            oa.setHead(head);
        }

        if (bodyTitle != null || bodyItems != null) {
            OaBody body = new OaBody();
            body.setTitle(bodyTitle);
            body.setForm(bodyItems);
            oa.setBody(body);
        }

        oa.setContent(content);
        oa.setAuthor(author);
        oa.setFileCount(fileCount);
        oa.setImage(image);
        oa.setMessageUrl(messageUrl);
        oa.setPcMessageUrl(pcMessageUrl);

        if (richNum != null || richUnit != null) {
            OaRich rich = new OaRich();
            rich.setNum(richNum);
            rich.setUnit(richUnit);
            oa.setRich(rich);
        }

        message.setOa(oa);
        return message;
    }
}
