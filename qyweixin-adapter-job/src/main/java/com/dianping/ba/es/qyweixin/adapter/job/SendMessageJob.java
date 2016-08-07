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
public class SendMessageJob extends AbstractMessageJob {
    // 标记作业是否在运行
    private static volatile boolean running = false;

    private Logger logger = LoggerFactory.getLogger(SendMessageJob.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        if (!running) {
            logger.debug("SendMessageJob start");
            try {
                running = true;
                // ugly getBean
                Map dataMap = context.getJobDetail().getJobDataMap();
                ApplicationContext ctx = (ApplicationContext) dataMap.get("applicationContext");
                this.setSendMessageService(ctx.getBean(SendMessageServiceImpl.class));
                // 获取未发送消息的guid  获取低优先级的消息，在发送超过阈值时暂停线程
                List<String> guidList = sendMessageService.getUnsendNormalPriorityMessageGuid();
                for (String guid : guidList) {
                    List<WeixinMessage> messageList = sendMessageService.getMessagesByGuid(guid, 0);
                    if (isBatchSend(messageList)) {
                        batchSend(messageList);
                    } else {
                        for (WeixinMessage message : messageList) {
                            send(message);
                        }
                    }
                }
            } finally {
                running = false;
                logger.debug("SendMessageJob end");
            }
        } else {
            logger.debug("a SendMessageJob is running, quit");
        }
    }
}
