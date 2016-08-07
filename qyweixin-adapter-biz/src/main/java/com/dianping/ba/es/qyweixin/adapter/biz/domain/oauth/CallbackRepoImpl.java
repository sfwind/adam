package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.CallbackDao;
import com.dianping.ba.es.qyweixin.adapter.biz.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by justin on 14-7-28.
 */
@Repository
public class CallbackRepoImpl implements CallbackRepo{
    @Autowired
    private CallbackDao callbackDao;

    @Override
    public Callback getCallback(String id){
        List<Callback> callbackList = callbackDao.queryByUUID(id);
        if(!CollectionUtils.isEmpty(callbackList)){
            return callbackList.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void saveCallback(Callback callback) {
        Date date = new Date();
        callback.setAddTime(DateUtil.parseDateToString(date));
        callback.setUpdateTime(DateUtil.parseDateToString(date));
        callbackDao.insert(callback);
    }

    @Override
    public void deleteCallback(Callback callback) {
        callbackDao.delete(callback.getUuid());
    }

    @Override
    public void updateCallback(Callback callback) {
        callbackDao.update(callback);
    }
}
