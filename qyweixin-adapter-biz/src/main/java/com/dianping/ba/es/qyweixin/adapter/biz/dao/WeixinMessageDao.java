package com.dianping.ba.es.qyweixin.adapter.biz.dao;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.WeixinMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeixinMessageDao {

    List<String> queryMessageWaitingSend();

    List<String> queryNormalPriorityMessageWaitingSend();

    List<String> queryHighPriorityMessageWaitingSend();

    List<String> queryMessageFailed();

    void insert(@Param("message") WeixinMessage weixinMessage);

    void update(@Param("message") WeixinMessage weixinMessage);

    List<WeixinMessage> queryMessagesByGuid(@Param("guid") String guid, @Param("status") int status);

    void batchUpdate(@Param("guid") String guid, @Param("status") int status, @Param("tryTimes") int tryTimes,
                     @Param("errcode") String errcode, @Param("errmsg") String errmsg, @Param("sendTime") String sendTime);

    int updateSendingMessage(@Param("id") int id);
}
