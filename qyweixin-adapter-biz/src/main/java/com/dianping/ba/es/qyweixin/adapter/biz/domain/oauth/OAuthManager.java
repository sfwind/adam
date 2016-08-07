package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

/**
 * Created by justin on 14-7-28.
 */
public interface OAuthManager {
    String getUserInfo(String code, String id);

    String saveCallback(int agentId, String callbackURL, Integer validTime);

    String getCallbackUrl(String id);

    String USER_NO_EXIST ="usernoexist";

    String TOO_BUSY = "systembusy";
}
