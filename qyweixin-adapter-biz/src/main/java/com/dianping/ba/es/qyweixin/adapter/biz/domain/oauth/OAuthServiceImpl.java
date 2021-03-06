package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.CallbackDao;
import com.dianping.ba.es.qyweixin.adapter.biz.dao.po.Callback;
import com.dianping.ba.es.qyweixin.adapter.biz.util.CommonUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.RestfulHandler;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * Created by justin on 16/8/13.
 */
@Service
public class OAuthServiceImpl implements OAuthService {
    @Autowired
    private RestfulHandler restfulHandler;
    @Autowired
    private CallbackDao callbackDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String redirectUrl(String callbackUrl) {
        String requestUrl = OAUTH_URL;
        Callback callback = new Callback();
        callback.setCallbackUrl(callbackUrl);
        String state = UUID.randomUUID().toString().replaceAll("-", "");
        callback.setState(state);
        callbackDao.insert(callback);

        Map<String,String> params = Maps.newHashMap();
        params.put("appid", ConfigUtils.getAppid());
        try {
            params.put("redirect_url", URLEncoder.encode(ConfigUtils.getRedirectUrl(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        params.put("state", state);
        requestUrl = CommonUtils.urlReplace(requestUrl, params);
        return requestUrl;
    }

    public String openId(String accessToken) {
        Callback callback = callbackDao.queryByAccessToken(accessToken);
        if(callback==null){
            logger.error("accessToken {} is invalid", accessToken);
            return null;
        }
        return callback.getOpenid();
    }

    public String refresh(String accessToken) {
        Callback callback = callbackDao.queryByAccessToken(accessToken);
        if(callback==null){
            logger.error("accessToken {} is invalid", accessToken);
            return null;
        }
        String requestUrl = REFRESH_TOKEN_URL;

        Map<String,String> params = Maps.newHashMap();
        params.put("appid", ConfigUtils.getAppid());
        params.put("refresh_token", callback.getRefreshToken());
        requestUrl = CommonUtils.urlReplace(requestUrl, params);
        String body = restfulHandler.get(requestUrl);
        Map<String, Object> result = CommonUtils.jsonToMap(body);
        String newAccessToken = (String)result.get("access_token");

        //刷新accessToken
        callbackDao.refreshToken(callback.getState(), newAccessToken);
        return newAccessToken;
    }

    public String accessToken(String code, String state) {
        Callback callback = callbackDao.queryByState(state);
        if(callback==null){
            logger.error("state {} is not found", state);
            return null;
        }
        String requestUrl = ACCESS_TOKEN_URL;

        Map<String,String> params = Maps.newHashMap();
        params.put("appid", ConfigUtils.getAppid());
        params.put("secret", ConfigUtils.getSecret());
        params.put("code", code);
        requestUrl = CommonUtils.urlReplace(requestUrl, params);
        String body = restfulHandler.get(requestUrl);
        Map<String, Object> result = CommonUtils.jsonToMap(body);

        String accessToken = (String)result.get("access_token");
        String openid = (String)result.get("openid");
        String refreshToken = (String)result.get("refresh_token");
        //更新accessToken，refreshToken，openid
        callbackDao.updateUserInfo(state, accessToken, refreshToken, openid);

        // callbackUrl增加参数access_token
        String callbackUrl = callback.getCallbackUrl();
        callbackUrl = CommonUtils.appendAccessToken(callbackUrl, accessToken);
        return callbackUrl;
    }
}
