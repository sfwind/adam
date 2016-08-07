package com.dianping.ba.es.qyweixin.adapter.dao;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.CallbackDao;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth.Callback;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.TestRollbackBase;
import com.dianping.ba.es.qyweixin.adapter.biz.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by justin on 14-7-29.
 */
public class CallbackDaoTest extends TestRollbackBase {
    @Autowired
    private CallbackDao callbackDao;
    @Test
    public void insertTest(){
        try {
            Callback callback = new Callback();
            callback.setUuid("123");
            callback.setCallbackCode("code");
            callback.setCallbackURL("http://someurl");
            callback.setAgentId(15);
            callback.setAddTime(DateUtil.parseDateToString(new Date()));
            callback.setUpdateTime(DateUtil.parseDateToString(new Date()));
            callback.setInvalidTime(new Date());
            int i = callbackDao.insert(callback);
            List<Callback> callbackList = callbackDao.queryByUUID("123");
            Assert.assertNotNull(callbackList);
            Callback first = callbackList.get(0);
            Assert.assertEquals(first.getId(), i);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest(){
        try {
            callbackDao.delete("123");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void queryTest(){
        try {
            List<Callback> callbackList = callbackDao.queryByUUID("123");
            Assert.assertNotNull(callbackList);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest(){
        try{
            List<Callback> callbackList = callbackDao.queryByUUID("123");
            Assert.assertNotNull(callbackList);
            Callback callback = callbackList.get(0);
            callback.setAgentId(15);
            callbackDao.update(callback);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
