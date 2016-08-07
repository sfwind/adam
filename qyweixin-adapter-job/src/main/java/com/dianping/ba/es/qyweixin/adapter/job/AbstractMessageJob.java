package com.dianping.ba.es.qyweixin.adapter.job;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.SendMessageService;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.SendMessageServiceImpl;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.WeixinMessage;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.DateUtil;
import com.dianping.ba.es.qyweixin.adapter.util.SenderThreadPoolFactory;
import com.dianping.ba.es.qyweixin.adapter.util.WeixinCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * Created by justin on 1/4/15.
 */
public abstract class AbstractMessageJob extends QuartzJobBean {
    private Logger logger = LoggerFactory.getLogger(AbstractMessageJob.class);

    protected SendMessageService sendMessageService;

    private final static int SUCCESS_CODE = 0;

    private final static int INVALID_USER_CODE = 40031;

    private final static int INVALID_CHARSET = 40033;

    private final static int EMPTY_CONTENT = 44004;

    private final static int SIZE_OUT_OF_LIMIT = 45002;

    private final static int MPNEWS_SIZE_OUT_OF_LIMIT = 45027;

    private final static int NO_PRIVILEGE_ACCESS_MODIFY = 60011;

    private final static int INVALID_USER_CODE_2 = 82001;

    private final static int INVALID_ENV_CODE = 99999;

    private final static int DELAY_SECOND = 60;

    private final static int HIGH_PRIORITY = 0;

    private boolean isOK(Integer errcode) {
        return errcode == SUCCESS_CODE;
    }

    protected void send(final WeixinMessage message) {
        if (ConfigUtils.getWhiteListSwitch()) {
            // 测试环境白名单
            if (!ConfigUtils.getWhiteListAgents().contains(String.valueOf(message.getAgentId()))) {
                // 发送频道不在白名单内
                logger.warn("target agentId is is not accessible in the env, guid is {}", message.getGuid());
                message.setStatus(2);
                message.setErrorCode(String.valueOf(INVALID_ENV_CODE));
                message.setErrorMsg("target agentId " + message.getAgentId() + " is not accessible in the env");
                sendMessageService.updateMessage(message);
                return;
            }
        }
        // 微信接口上限校验  低优先级消息发送时才进行接口阈值的校验
        if (message.getPriority() != HIGH_PRIORITY) {
            WeixinCounter.invoke(DateUtil.getMinuteByDate(new Date()));
        }
        int result = sendMessageService.updateSendingMessage(message.getId());
        if (result == 0) {
            return;
        }
        // 多线程发送
        SenderThreadPoolFactory.create(message.getPriority()).execute(new Thread(new Runnable() {
            public void run() {
                Object[] result = sendMessageService.sendMessage(message);
                if (result[0] != null && result[0] instanceof Integer) {
                    Integer errcode = (Integer) result[0];
                    // 正常情况
                    if (isOK(errcode)) {
                        message.setStatus(1);
                        message.setErrorCode("0");
                        if (result[1] != null) {
                            message.setErrorMsg(result[1].toString());
                        }
                        message.setSendTime(DateUtil.convertDateToString(new Date()));
                        // 忽略的情况
                    } else if (isSendIgnore(errcode)) {
                        logger.warn("ignore error code is {}", result[0]);
                        message.setStatus(2);
                        message.setErrorCode(errcode.toString());
                        if (result[1] != null) {
                            logger.warn("ignore error is {}", result[1]);
                            message.setErrorMsg(result[1].toString());
                        }
                        // 错误
                    } else {
                        logger.error("error code is {}", result[0]);
                        message.setStatus(-1);
                        message.setErrorCode(errcode.toString());
                        if (result[1] != null) {
                            logger.error("error is {}", result[1]);
                            message.setErrorMsg(result[1].toString());
                        }
                    }
                    int retry = message.getTryTimes() + 1;
                    if (retry > 10) {
                        // 大于10次不再重试
                        message.setStatus(2);
                    }
                    message.setTryTimes(retry);
                    sendMessageService.updateMessage(message);
                } else {
                    logger.error("unknown error, send message id={}", message.getId());
                }
            }
        }), "sendWeiXinMessage");
    }


    private boolean isDelay(WeixinMessage message) {
        try {
            if (message.getPriority() != 0) {
                return false;
            }
            if (message.getStatus() != 0) {
                // 非首次发送消息，直接pass
                return false;
            }
            long addTimeSecond = DateUtil.parseStringToDate(message.getAddTime()).getTime();
            long now = new Date().getTime();
            if ((now - addTimeSecond) / 1000 > DELAY_SECOND) {
                return true;
            }
        } catch (Exception e) {
            // ignore
        }
        return false;
    }


    protected void sendBatch(WeixinMessage message) {
        if (ConfigUtils.getWhiteListSwitch()) {
            // 测试环境白名单
            if (!ConfigUtils.getWhiteListAgents().contains(String.valueOf(message.getAgentId()))) {
                // 发送频道不在白名单内
                logger.warn("target agentId is is not accessible in the env, guid is {}", message.getGuid());
                message.setStatus(2);
                message.setErrorCode(String.valueOf(INVALID_ENV_CODE));
                message.setErrorMsg("target agentId " + message.getAgentId() + " is not accessible in the env");
                sendMessageService.updateMessage(message);
                return;
            }
        }
        // 微信接口上限校验  低优先级消息发送时才进行接口阈值的校验
        if (message.getPriority() != HIGH_PRIORITY) {
            WeixinCounter.invoke(DateUtil.getMinuteByDate(new Date()));
        }
        Object[] result = sendMessageService.sendMessage(message);
        int status = 0;
        int tryTimes = message.getTryTimes() + 1;
        String sendTime = null;
        String errcode = "";
        String errmsg = null;
        if (result[0] != null && result[0] instanceof Integer) {
            Integer code = (Integer) result[0];
            // 正常情况
            if (isOK(code)) {
                status = 1;
                errcode = code.toString();
                if (result[1] != null) {
                    errmsg = (String) result[1].toString();
                }
                sendTime = DateUtil.convertDateToString(new Date());
                // 忽略的情况
            } else if (isBatchSendIgnore(code)) {
                logger.warn("ignore error code is {}", result[0]);
                status = 2;
                errcode = code.toString();
                if (result[1] != null) {
                    logger.warn("ignore error is {}", result[1]);
                    errmsg = result[1].toString();
                }
                sendTime = DateUtil.convertDateToString(new Date());
                // 错误
            } else {
                logger.error("error code is {}", result[0]);
                status = -1;
                errcode = code.toString();
                if (result[1] != null) {
                    logger.error("error is {}", result[1]);
                    errmsg = result[1].toString();
                }
            }
            sendMessageService.batchUpdate(message.getGuid(), status, tryTimes, errcode, errmsg, sendTime);
        } else {
            logger.error("unknown error, send message id={}", message.getId());
        }
    }

    private boolean isSendIgnore(Integer errcode) {
        if (errcode == INVALID_USER_CODE ||
                errcode == INVALID_USER_CODE_2 ||
                errcode == NO_PRIVILEGE_ACCESS_MODIFY ||
                errcode == EMPTY_CONTENT ||
                errcode == SIZE_OUT_OF_LIMIT ||
                errcode == INVALID_CHARSET ||
                errcode == MPNEWS_SIZE_OUT_OF_LIMIT) {
            return true;
        }
        return false;
    }

    private boolean isBatchSendIgnore(Integer errcode) {
        if (errcode == INVALID_USER_CODE ||
                errcode == EMPTY_CONTENT ||
                errcode == SIZE_OUT_OF_LIMIT ||
                errcode == INVALID_CHARSET) {
            return true;
        }
        return false;
    }

    public void setSendMessageService(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }


    protected boolean isBatchSend(List messageList) {
        return SendMessageServiceImpl.isMulti(messageList);
    }

    protected void batchSend(List<WeixinMessage> messageList) {
        // 发送批量信息
        WeixinMessage message = messageList.get(0);
        StringBuilder sb = new StringBuilder();
        // 拼接所有的接收者
        for (WeixinMessage weixinMessage : messageList) {
            sb.append(weixinMessage.getEmployeeId());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        message.setEmployeeId(sb.toString());
        sendBatch(message);
    }
}
