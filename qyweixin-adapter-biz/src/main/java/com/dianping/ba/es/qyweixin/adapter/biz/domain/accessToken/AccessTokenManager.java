package com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken;


public interface AccessTokenManager {
    String getAccessToken();

    String refreshAccessToken();
}
