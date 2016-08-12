package com.dianping.ba.es.qyweixin.adapter.biz.domain;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.po.Account;
import com.dianping.ba.es.qyweixin.adapter.biz.domain.account.AccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by justin on 16/8/12.
 */
public class AccountServiceTest extends TestBase{
    @Autowired
    private AccountService accountService;

    @Test
    public void testGetAccount() throws Exception {
        Account account = accountService.getAccount("oK881wQekezGpw6rq790y_vAY_YY");
    }
}
