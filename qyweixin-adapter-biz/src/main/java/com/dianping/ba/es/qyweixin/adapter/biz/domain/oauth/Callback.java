package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

import java.util.Date;

/**
 * Created by justin on 14-7-28.
 */

public class Callback {
    private int id;
    private String uuid;
    private int agentId;
    private String callbackURL;
    private String callbackCode;
    private String addTime;
    private String updateTime;
    private String userId;
    private Date invalidTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCallbackCode() {
        return callbackCode;
    }

    public void setCallbackCode(String callbackCode) {
        this.callbackCode = callbackCode;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getCallbackURL() {
        return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {
        this.callbackURL = callbackURL;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }
}
