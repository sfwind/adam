package com.dianping.ba.es.qyweixin.adapter.biz.domain;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.TemplateMessage;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.TemplateMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by justin on 16/8/12.
 */
public class TemplateMessageServiceTest extends TestBase{
    @Autowired
    private TemplateMessageService templateMessageService;

    @Test
    public void testSendMessage() throws Exception {
        TemplateMessage message = new TemplateMessage();
        message.setTemplate_id("YYQGZid6S5Ff74i7O3nAsUxa--tX5NBTIzr1JKY2ifQ");
        message.setTouser("oK881wQekezGpw6rq790y_vAY_YY");
        TemplateMessage.Keyword keyword = new TemplateMessage.Keyword();
        keyword.setColor("#44B549");
        keyword.setValue("风之伤");
        message.getData().put("name", keyword);

        templateMessageService.sendMessage(message);
    }
}
