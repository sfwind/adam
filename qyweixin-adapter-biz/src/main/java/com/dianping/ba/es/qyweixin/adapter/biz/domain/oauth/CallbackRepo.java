package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

/**
 * Created by justin on 14-7-28.
 */
public interface CallbackRepo {
    Callback getCallback(String id);

    void saveCallback(Callback callback);

    void deleteCallback(Callback callback);

    void updateCallback(Callback callback);
}
