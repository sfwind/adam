package com.dianping.ba.es.qyweixin.adapter.biz.domain.account;

import com.dianping.ba.es.qyweixin.adapter.biz.dao.po.Account;

/**
 * Created by justin on 16/8/10.
 */
public interface AccountService {
    /**
     * 根据openid获取用户的详细信息
     * */
    Account getAccount(String openId);
}
