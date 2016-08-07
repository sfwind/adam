package com.dianping.ba.es.qyweixin.adapter.biz.service;


import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.SendMessageService;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.SendMessageServiceImpl;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.WeixinMessage;
import com.dianping.ba.es.qyweixin.adapter.biz.entity.*;
import com.dianping.ba.es.qyweixin.adapter.biz.entity.media.*;
import com.dianping.ba.es.qyweixin.adapter.biz.exception.QyWeixinAdaperException;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl extends BaseService {
//    @Autowired
//    private RestfulHandler restfulHandler;
//    @Autowired
//    private AuditRepo auditRepo;
    @Autowired
    private SendMessageService sendMessageService;


    public void sendMessage(MessageDto messageDto) throws QyWeixinAdaperException {
        try{
            sendMessageService.addMessageToQueue(getMsgList(messageDto));
        }catch(RuntimeException e){
            logger.error("internal exception", e);
            throw INTERNAL_EXCEPTION;
        }
    }

    public void sendTextMessage(TextMessageDto textMessageDto) throws QyWeixinAdaperException {
        try{
            textMessageDto.setMediaDto(textMessageDto.getTextDto());
            sendMessage(textMessageDto);
        }catch(RuntimeException e){
            logger.error("internal exception", e);
            throw INTERNAL_EXCEPTION;
        }
    }

//    private Map _sendMessage(MessageDto messageDto) {
//
//        auditRepo.auditLog(-1, "message", "message", messageDto.getMediaDto().toString(), null);
//
//        Map params = new HashMap();
//        String toUser = "";
//        if (messageDto.getTouser() != null) {
//            for (String userId : messageDto.getTouser()) {
//                toUser = toUser + "|" + userId;
//            }
//        }
//        String toParty = "";
//        if (messageDto.getToparty() != null) {
//            for (Integer partyId : messageDto.getToparty()) {
//                toParty = toParty + "|" + partyId;
//            }
//        }
//        messageDto.getMediaDto().fillParameter(params);
//
//        params.put("touser", toUser.length() < 1 ? toUser : toUser.substring(1));
//        params.put("toparty", toParty.length() < 1 ? toParty : toParty.substring(1));
//        params.put("agentid", messageDto.getAgentid());
//        params.put("safe", messageDto.getSafe());
//
//        ResponseEntity<Map> response = restfulHandler.post(String.valueOf(messageDto.getAgentid()), ConfigUtils.sendMessageUrl(), params);
//
//        return response.getBody();
//    }

    /**
     * 大于1000，拆分
     * @param dto
     * @return
     */
    private List<List<WeixinMessage>> getMsgList(MessageDto dto){
        int numPerMsg = ConfigUtils.getBatchSendLimit();
        List<List<WeixinMessage>> result = Lists.newArrayList();
        List<String> users = dto.getTouser();
        if(users.size() > numPerMsg){
            int listNum = users.size()/numPerMsg;
            for(int i = 0; i <listNum; i++){
                List<WeixinMessage> list =mapper(dto,users.subList(i*numPerMsg,(i+1)*numPerMsg));
                result.add(list);
            }
            List<WeixinMessage> list =mapper(dto,users.subList(listNum*numPerMsg,users.size()));
            result.add(list);
        }else{
            result.add(Lists.newArrayList(mapper(dto,users)));
        }
        return  result;
    }
    private List<WeixinMessage> mapper(MessageDto messageDto,List<String> subUserList){
        List<WeixinMessage> messages = new ArrayList<WeixinMessage>();
        String guid = UUID.randomUUID().toString();
        List<String> users = subUserList;
        boolean isMulti = SendMessageServiceImpl.isMulti(users);
        for(String user:users){
            WeixinMessage message = new WeixinMessage();
            message.setEmployeeId(user);
            message.setGuid(guid);
            message.setMulti(isMulti);
            message.setTryTimes(0);
            message.setAgentId(messageDto.getAgentid());
            message.setIsSafe(messageDto.getSafe());
            message.setPriority(messageDto.getPriority());
            mediaMapper(message, messageDto.getMediaDto());
            messages.add(message);
        }

        return messages;
    }

    private void mediaMapper(WeixinMessage message, MediaDto mediaDto){
        if(mediaDto instanceof TextDto){
            TextDto textDto = (TextDto)mediaDto;
            message.setMessageType(1);
            message.setContent(textDto.getContent());
        }else if(mediaDto instanceof NewsDto){
            NewsDto newsDto = (NewsDto)mediaDto;
            message.setMessageType(2);
            // 只支持一篇文章
            NewsDto.Article article = newsDto.getArticles().get(0);
            message.setTitle(article.getTitle());
            message.setContent(article.getDescription());
            message.setUrl(article.getUrl());
            message.setPicUrl(article.getPicurl());
        }else if(mediaDto instanceof MpNewsDto){
            MpNewsDto mpNewsDto = (MpNewsDto) mediaDto;
            message.setMessageType(3);
            message.setContent(mpNewsDto.getMedia_id());
        }
    }
}
