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

    String OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={appid}&redirect_uri={redirect_url}&response_type=code&scope=snsapi_base&state={state}#wechat_redirect";

    String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid={appid}&grant_type=refresh_token&refresh_token={refresh_token}";

    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";
}
