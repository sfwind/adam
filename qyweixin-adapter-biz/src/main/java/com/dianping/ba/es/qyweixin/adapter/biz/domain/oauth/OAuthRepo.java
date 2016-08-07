package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

/**
 * Created by justin on 14-7-28.
 */
public interface OAuthRepo {
    String getUserInfo(String code, Callback callback);
}
