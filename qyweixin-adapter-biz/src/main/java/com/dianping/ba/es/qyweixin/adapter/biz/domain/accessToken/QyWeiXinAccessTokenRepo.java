package com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken;


public interface QyWeiXinAccessTokenRepo {
    String getAccessToken();

    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={secret}";
}
