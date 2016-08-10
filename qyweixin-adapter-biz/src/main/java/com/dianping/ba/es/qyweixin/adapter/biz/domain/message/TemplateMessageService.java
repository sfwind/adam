package com.dianping.ba.es.qyweixin.adapter.biz.domain.message;

/**
 * Created by justin on 16/8/10.
 */
public interface TemplateMessageService {
    /**
     * 发送模板消息
     * @return 返回发送结果
     * */
    boolean sendMessage(TemplateMessage templateMessage);

    /**
     * 根据模板库中的编号获取模板真实id
     * @param templateShortId
     * 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * */
    String getTemplateId(String templateShortId);
}
