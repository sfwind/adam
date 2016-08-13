package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

/**
 * Created by justin on 14-7-28.
 */
public interface OAuthService {
    /**
     * 组装微信授权页的url，记录回调url
     * */
    String redirectUrl(String callbackUrl);
    /**
     * 根据code，获取accessToken，并在回调url中拼接参数accessToken返回
     * */
    String accessToken(String code, String state);
    /**
     * 根据accessToken，获取授权用户的openid
     * */
    String openId(String accessToken);
    /**
     * 刷新accessToken
     * */
    String refresh(String accessToken);
}
