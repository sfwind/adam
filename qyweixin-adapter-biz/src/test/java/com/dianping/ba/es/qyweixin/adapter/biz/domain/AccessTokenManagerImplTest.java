package com.dianping.ba.es.qyweixin.adapter.biz.domain;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken.AccessTokenManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertTrue;


@Component
public class AccessTokenManagerImplTest extends TestBase {

    @Autowired
    private AccessTokenManager accessTokenManager;

    @Test
    public void testGetAccessToken() throws Exception {
        String result;
        String result2;
        result = accessTokenManager.getAccessToken();
        result2 = accessTokenManager.getAccessToken();
        assertTrue(result2.equals(result));
    }
}
