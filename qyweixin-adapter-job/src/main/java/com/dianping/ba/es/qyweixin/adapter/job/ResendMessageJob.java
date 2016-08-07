package com.dianping.ba.es.qyweixin.adapter.job;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.SendMessageServiceImpl;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.WeixinMessage;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * Created by justin on 1/4/15.
 */
public class ResendMessageJob extends AbstractMessageJob {
    private Logger logger = LoggerFactory.getLogger(ResendMessageJob.class);

    private static volatile boolean running = false;


    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        if (!running) {
            logger.debug("ReSendMessageJob start");
            try {
                running = true;
                // ugly getBean
                Map dataMap = context.getJobDetail().getJobDataMap();
                ApplicationContext ctx = (ApplicationContext) dataMap.get("applicationContext");
                this.setSendMessageService(ctx.getBean(SendMessageServiceImpl.class));
                // 获取未发送消息的guid
                List<String> guidList = sendMessageService.getFailedMessageGuid();
                for (String guid : guidList) {
                    List<WeixinMessage> messageList = sendMessageService.getMessagesByGuid(guid, -1);
                    //重发使用单个发送逻辑
                    for (WeixinMessage message : messageList) {
                        send(message);
                    }
                }
            }finally {
                running = false;
                logger.debug("ReSendMessageJob end");
            }
        }else{
            logger.debug("a SendMessageJob is running, quit");

        }
    }

}
