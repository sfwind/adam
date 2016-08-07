package com.dianping.ba.es.qyweixin.adapter.biz.domain.message;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * Created by justin on 14-7-24.
 */
public class Message implements Serializable{
    /**
     * 微信企业号
     */
    private String toUserName;

    /**
     * 员工 UserID
     */
    private String fromUserName;

    /**
     * 消息创建时间 (整型)
     */
    private int createTime;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 应用的 id
     */
    private int agentID;

    public boolean isIgnore(){
        return false;
    }

    @XmlElement(name = "ToUserName")
    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    @XmlElement(name = "FromUserName")
    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    @XmlElement(name = "CreateTime")
    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    @XmlElement(name = "MsgType")
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @XmlElement(name = "AgentID")
    public int getAgentID() {
        return agentID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    // to override
    public String getBusiness(){
        return "";
    }
}
