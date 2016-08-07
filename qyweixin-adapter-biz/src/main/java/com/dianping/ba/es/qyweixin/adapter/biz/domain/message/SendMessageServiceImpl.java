package com.dianping.ba.es.qyweixin.adapter.biz.domain.message;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.WeixinMessageDao;
import com.dianping.ba.es.qyweixin.adapter.biz.entity.MessageDto;
import com.dianping.ba.es.qyweixin.adapter.biz.entity.media.MpNewsDto;
import com.dianping.ba.es.qyweixin.adapter.biz.entity.media.NewsDto;
import com.dianping.ba.es.qyweixin.adapter.biz.entity.media.TextDto;
import com.dianping.ba.es.qyweixin.adapter.biz.exception.ErrorConstants;
import com.dianping.ba.es.qyweixin.adapter.biz.exception.QyWeixinAdaperException;
import com.dianping.ba.es.qyweixin.adapter.biz.service.BaseService;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.RestfulHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by justin on 1/4/15.
 */
@Service
public class SendMessageServiceImpl extends BaseService implements SendMessageService {
    @Autowired
    private RestfulHandler restfulHandler;
    @Autowired
    private WeixinMessageDao weixinMessageDao;

    public Object[] sendMessage(WeixinMessage message) {
        Object[] err = new Object[2];
        try {
            Map result = _sendMessage(resolveMessage(message));
            if (isAccessTokenExpired(result, String.valueOf(message.getAgentId()))) {
                // retry
                result = _sendMessage(resolveMessage(message));
            }
            validateResult(result);
            err[0] = 0;
            err[1] = result.get("errmsg");
        } catch (RuntimeException e) {
            logger.error("internal exception", e);
            err[0] = ErrorConstants.INTERNAL_ERROR;
            err[1] = ErrorConstants.INTERNAL_ERROR_MSG;
        } catch (QyWeixinAdaperException e) {
            err[0] = e.getErrcode();
            err[1] = e.getMessage();
        }

        return err;
    }

    private MessageDto resolveMessage(WeixinMessage message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setAgentid(message.getAgentId());
        messageDto.setSafe(message.isSafe());
        List<String> touser = new ArrayList<String>();
        String employeeId = message.getEmployeeId();
        // 发送给多人时，employeeId用,分隔
        if (employeeId.contains(",")) {
            String[] receivers = employeeId.split(",");
            for (String recv : receivers) {
                touser.add(recv);
            }
        } else {
            touser.add(employeeId);
        }
        messageDto.setTouser(touser);
        // 构造不同类型的message
        constructMessage(message, messageDto);

        return messageDto;
    }

    private Map _sendMessage(MessageDto messageDto) {
        Map params = new HashMap();
        String toUser = "";
        if (messageDto.getTouser() != null) {
            for (String userId : messageDto.getTouser()) {
                toUser = toUser + "|" + userId;
            }
        }
        String toParty = "";
        if (messageDto.getToparty() != null) {
            for (Integer partyId : messageDto.getToparty()) {
                toParty = toParty + "|" + partyId;
            }
        }
        messageDto.getMediaDto().fillParameter(params);

        params.put("touser", toUser.length() < 1 ? toUser : toUser.substring(1));
        params.put("toparty", toParty.length() < 1 ? toParty : toParty.substring(1));
        params.put("agentid", messageDto.getAgentid());
        params.put("safe", messageDto.getSafe());

//        ResponseEntity<Map> response = restfulHandler.post(String.valueOf(messageDto.getAgentid()), ConfigUtils.sendMessageUrl(), params);

        return null;
    }

    public boolean addMessageToQueue(WeixinMessage message) {
        weixinMessageDao.insert(message);
        return true;
    }

    public void updateMessage(WeixinMessage message) {
        weixinMessageDao.update(message);
    }

    public List<String> getUnsendMessageGuid() {
        return weixinMessageDao.queryMessageWaitingSend();
    }

    public List<String> getUnsendNormalPriorityMessageGuid() {
        return weixinMessageDao.queryNormalPriorityMessageWaitingSend();
    }

    public List<String> getUnsendHighPriorityMessageGuid() {
        return weixinMessageDao.queryHighPriorityMessageWaitingSend();
    }

    public List<String> getFailedMessageGuid() {
        return weixinMessageDao.queryMessageFailed();
    }

    public List<WeixinMessage> getMessagesByGuid(String guid, int status) {
        return weixinMessageDao.queryMessagesByGuid(guid, status);
    }

    public void batchUpdate(String guid, int status, int tryTimes, String errcode, String errmsg, String sendTime) {
        weixinMessageDao.batchUpdate(guid, status, tryTimes, errcode, errmsg, sendTime);
    }

    public int updateSendingMessage(int id) {
        return weixinMessageDao.updateSendingMessage(id);
    }

    private void constructMessage(WeixinMessage message, MessageDto messageDto) {
        switch (message.getMessageType()) {
            case 1:
                TextDto textDto = new TextDto();
                textDto.setContent(message.getContent());
                messageDto.setMediaDto(textDto);
                break;
            case 2:
                NewsDto newsDto = new NewsDto();
                NewsDto.Article article = new NewsDto.Article(message.getTitle(), message.getContent(), message.getUrl(), message.getPicUrl());
                newsDto.addArticle(article);
                messageDto.setMediaDto(newsDto);
                break;
            case 3:
                MpNewsDto mpNewsDto = new MpNewsDto();
                mpNewsDto.setMedia_id(message.getContent());
                messageDto.setMediaDto(mpNewsDto);
                break;
            default:
                logger.error("{} is not support", message.getMessageType());
                break;
        }
    }

    public static boolean isMulti(List list) {
        return list.size()>1 && ConfigUtils.getBatchSendSwitch();
    }

    public boolean addMessageToQueue(List<List<WeixinMessage>> messages) {
        for(List<WeixinMessage> subMsgs : messages) {
            for (WeixinMessage message : subMsgs) {
                addMessageToQueue(message);
            }
        }
        return true;
    }
}
