package com.dianping.ba.es.qyweixin.adapter.biz.domain;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken.AccessTokenService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;


public class AccessTokenServiceTest extends TestBase {

    @Autowired
    private AccessTokenService accessTokenService;

    @Test
    public void testGetAccessToken() throws Exception {
        String result;
        String result2;
        result = accessTokenService.getAccessToken();
        result2 = accessTokenService.getAccessToken();
        assertTrue(result2.equals(result));
    }
}
