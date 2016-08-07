package com.dianping.ba.es.qyweixin.adapter.biz.domain;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.signature.JsSignatureManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yangyuchen on 15-1-30.
 */
public class JsSignatureManagerTest extends TestBase {
    @Autowired
    private JsSignatureManager jsSignatureManager;

    @Test
    public void getJsSignatureTest() {
        jsSignatureManager.getJsSignature(15, "http://qywx.51ping.com/app/checkin.html", true);
    }
}
