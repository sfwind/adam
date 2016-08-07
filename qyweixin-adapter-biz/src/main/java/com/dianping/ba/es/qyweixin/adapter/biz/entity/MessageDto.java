package com.dianping.ba.es.qyweixin.adapter.biz.entity;


import com.dianping.ba.es.qyweixin.adapter.biz.entity.media.MediaDto;

import java.io.Serializable;
import java.util.List;


public class MessageDto implements Serializable {
    // @all向全员发送
    private List<String> touser;
    private List<Integer> touserLoginId;
    private List<Integer> toparty;
    private int agentid;
    private MediaDto mediaDto;
    private int safe;
    private int priority = BATCH_MSG;

    // 优先级常量定义
    public static final int SINGLE_MSG = 0; //适用于单条发送的消息，将优先发送
    public static final int BATCH_MSG = 1;  //适用于大批量发送消息
    public static final int INSENSITIVE = 2;//适用于及时性不敏感的消息


    public List<String> getTouser() {
        return touser;
    }

    public void setTouser(List<String> touser) {
        this.touser = touser;
    }

    public List<Integer> getTouserLoginId() {
        return touserLoginId;
    }

    public void setTouserLoginId(List<Integer> touserLoginId) {
        this.touserLoginId = touserLoginId;
    }

    public List<Integer> getToparty() {
        return toparty;
    }

    public void setToparty(List<Integer> toparty) {
        this.toparty = toparty;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public MediaDto getMediaDto() {
        return mediaDto;
    }

    public void setMediaDto(MediaDto mediaDto) {
        this.mediaDto = mediaDto;
    }

    public int getSafe() {
        return safe;
    }

    public void setSafe(int safe) {
        this.safe = safe;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
