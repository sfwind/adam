package com.dianping.ba.es.qyweixin.adapter.biz.domain.signature;

/**
 * Created by yangyuchen on 15-1-30.
 */
public interface JsSignatureManager {
    public JsSignature getJsSignature(int agentId, String url, boolean refresh);
}
