package com.dianping.ba.es.qyweixin.adapter.biz.entity;

/**
 * Created by justin on 14-7-28.
 */
public class LinkMessageDto extends MessageDto {
    private String linkMessage;

    private String callbackURL;

    private String callbackCode;

    public String getCallbackCode() {
        return callbackCode;
    }

    public void setCallbackCode(String callbackCode) {
        this.callbackCode = callbackCode;
    }

    public String getLinkMessage() {
        return linkMessage;
    }

    public void setLinkMessage(String linkMessage) {
        this.linkMessage = linkMessage;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }
}
