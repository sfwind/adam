package com.dianping.ba.es.qyweixin.adapter.biz.domain.secret;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.TestRollbackBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created by justin on 9/23/14.
 */
public class CorpSecretManagerImplTest extends TestRollbackBase {
    @Autowired
    private CorpSecretManager service;

    @Test
    public void testGetCorpSecret() throws Exception {

        String result = service.getCorpSecretFromDB("15");
        assertEquals("gZ7r39gflrSLFB3p74kykyhpfhh-hp5jFynMFs4mBjo", result);
    }
}
