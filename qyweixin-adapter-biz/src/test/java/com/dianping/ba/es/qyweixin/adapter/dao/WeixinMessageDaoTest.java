package com.dianping.ba.es.qyweixin.adapter.dao;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.WeixinMessageDao;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.TestBase;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.message.WeixinMessage;
import com.dianping.ba.es.qyweixin.adapter.biz.util.DateUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yangyuchen on 15-1-4.
 */
public class WeixinMessageDaoTest extends TestBase {
    @Autowired
    WeixinMessageDao weixinMessageDao;

    @Ignore
    public void messageTest() {
        WeixinMessage weixinMessage = new WeixinMessage();
        weixinMessage.setAgentId(15);
        weixinMessage.setContent("测试");
        weixinMessage.setEmployeeId("9001476");
        weixinMessage.setIsSafe(0);
        weixinMessage.setStatus(0);
        weixinMessage.setTryTimes(0);
        weixinMessage.setMulti(false);
        weixinMessage.setGuid(UUID.randomUUID().toString());
        weixinMessage.setSendTime(DateUtil.convertDateToString(new Date()));

        weixinMessageDao.insert(weixinMessage);
    }

    @Test
    public void updateTest() {
        List<String> guids = weixinMessageDao.queryMessageWaitingSend();

        if(!CollectionUtils.isEmpty(guids)){
            for(String guid:guids){
                List<WeixinMessage> weixinMessageList = weixinMessageDao.queryMessagesByGuid(guid, 0);
                if(!CollectionUtils.isEmpty(weixinMessageList)){
                    weixinMessageDao.update(weixinMessageList.get(0));
                }
            }
        }
    }

    @Test
    public void queryTest() {
        List<String> guids = weixinMessageDao.queryMessageWaitingSend();

        if(!CollectionUtils.isEmpty(guids)){
            for(String guid:guids){
                List<WeixinMessage> weixinMessageList = weixinMessageDao.queryMessagesByGuid(guid, 0);
            }
        }
        guids = weixinMessageDao.queryMessageFailed();
        for(String guid:guids){
            List<WeixinMessage> weixinMessageList = weixinMessageDao.queryMessagesByGuid(guid, -1);
        }
    }

    @Test
    public void batchUpdateTest(){
        weixinMessageDao.batchUpdate("12e1f8a0-ede5-4876-a0b1-ca6bd99ecc84", 1, 1, "0", null, DateUtil.convertDateToString(new Date()));
    }
}
