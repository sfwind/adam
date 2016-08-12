package com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken;


public interface AccessTokenService {
    String getAccessToken();

    String refreshAccessToken();
}
