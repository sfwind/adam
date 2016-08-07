package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

import com.dianping.ba.es.qyweixin.adapter.biz.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by justin on 14-7-28.
 */
@Service
public class OAuthManagerImpl implements OAuthManager{
    @Autowired
    private OAuthRepo oAuthRepo;
    @Autowired
    private CallbackRepo callbackRepo;


    public String getUserInfo(String code, String id) {
        Callback callback = callbackRepo.getCallback(id);
        if(callback==null){
            return USER_NO_EXIST;
        }
        String userInfo = oAuthRepo.getUserInfo(code, callback);
        callback.setUserId(userInfo);
        callbackRepo.updateCallback(callback);
        return userInfo;
    }

    public String saveCallback(int agentId, String callbackURL, Integer validTime){
        Callback callback = new Callback();
        String id = UUID.randomUUID().toString();
        callback.setUuid(id);
        callback.setAgentId(agentId);
        callback.setCallbackURL(callbackURL);
        if(validTime!=null){
            callback.setInvalidTime(DateUtil.timeAfter(new Date(), Calendar.MINUTE, validTime));
        }

        callbackRepo.saveCallback(callback);
        return id;
    }


    public String getCallbackUrl(String id) {
        Callback callback = callbackRepo.getCallback(id);
        if(callback==null){
            return "";
        }else{
            return callback.getCallbackURL();
        }
    }

}
