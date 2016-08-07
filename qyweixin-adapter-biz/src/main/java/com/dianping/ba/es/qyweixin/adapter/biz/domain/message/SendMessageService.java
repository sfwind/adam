package com.dianping.ba.es.qyweixin.adapter.biz.domain.message;

import java.util.List;

/**
 * Created by justin on 1/4/15.
 */
public interface SendMessageService {
    /** 发送消息 */
    public Object[] sendMessage(WeixinMessage message);
    /** 将消息放到消息队列 */
    public boolean addMessageToQueue(WeixinMessage message);
    /** 将多条消息批量放到消息队列 */
    public boolean addMessageToQueue(List<List<WeixinMessage>> messages);
    /** 更新消息 */
    public void updateMessage(WeixinMessage message);
    /** 获取未发送的消息guid */
    public List<String> getUnsendMessageGuid();
    /** 获取未发送的低优先级消息guid */
    public List<String> getUnsendNormalPriorityMessageGuid();
    /** 获取未发送的高优先级消息guid */
    public List<String> getUnsendHighPriorityMessageGuid();
    /** 获取发送失败的消息的guid */
    public List<String> getFailedMessageGuid();
    /** 获取发送失败的消息的guid */
    public List<WeixinMessage> getMessagesByGuid(String guid, int status);
    /** 批量更新状态 */
    public void batchUpdate(String guid, int status, int tryTimes, String errcode, String errmsg, String sendTime);
    /** 更新待发送消息的状态 */
    public int updateSendingMessage(int id);
}
