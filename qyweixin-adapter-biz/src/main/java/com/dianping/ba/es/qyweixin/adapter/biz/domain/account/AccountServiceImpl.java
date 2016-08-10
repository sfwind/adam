package com.dianping.ba.es.qyweixin.adapter.biz.domain.account;

import com.dianping.ba.es.qyweixin.adapter.biz.util.CommonUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.RestfulHandler;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by justin on 16/8/10.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    public RestfulHandler restfulHandler;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Account getAccount(String openId) {
        String url = ConfigUtils.getUserInfoUrl();
        Account account = new Account();

        String body = restfulHandler.get(url);
        Map<String, Object> result = CommonUtils.jsonToMap(body);

        try {
            BeanUtils.populate(account, result);
            return account;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
