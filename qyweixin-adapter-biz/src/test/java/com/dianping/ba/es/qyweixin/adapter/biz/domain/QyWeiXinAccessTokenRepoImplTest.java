package com.dianping.ba.es.qyweixin.adapter.biz.domain;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken.QyWeiXinAccessTokenRepoImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class QyWeiXinAccessTokenRepoImplTest extends TestBase {

    @Autowired
    private QyWeiXinAccessTokenRepoImpl service;

    @Test
    public void testGetAccessToken() throws Exception {

        service = new QyWeiXinAccessTokenRepoImpl();
        String result = service.getAccessToken();

    }

}
