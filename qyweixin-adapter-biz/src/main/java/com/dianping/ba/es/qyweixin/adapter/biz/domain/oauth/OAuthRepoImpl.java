package com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken.AccessTokenManager;
import com.dianping.ba.es.qyweixin.adapter.biz.service.BaseService;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.RestfulHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by justin on 14-7-28.
 */
@Repository
public class OAuthRepoImpl implements OAuthRepo {
    @Autowired
    private RestfulHandler restfulHandler;
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthRepoImpl.class);
    @Autowired
    private AccessTokenManager accessTokenManager;

    public String getUserInfo(String code, Callback callback) {
        int agentId = callback.getAgentId();
        Map body = _getUserInfo(code, agentId);
        Integer errorCode = (Integer) body.get("errcode");
        if (errorCode != null) {
            LOGGER.error("weixin communication error, errmsg=" + body.get("errmsg") + ", errcode=" + errorCode + ", code=" + code);
            if (errorCode == BaseService.ACCESS_TOKEN_EXPIRED || errorCode == BaseService.ACCESS_TOKEN_EXPIRED_NEW) {
                // refresh access token
                accessTokenManager.refreshAccessToken();
                body = _getUserInfo(code, agentId);
            }
            if (errorCode == BaseService.USER_NO_EXIST) {
                //user not follow or is not employee
                return OAuthManager.USER_NO_EXIST;
            }
            if (errorCode == BaseService.API_FREQ_OUT_OF_LIMIT) {
                // api freq out of limit
                return OAuthManager.TOO_BUSY;
            }
            if (errorCode == BaseService.INVALID_CODE) {
                // invalid code, try again
                body = _getUserInfo(code, agentId);
                errorCode = (Integer) body.get("errcode");
                // if invalid again, return too busy
                if (errorCode == BaseService.INVALID_CODE) {
                    return OAuthManager.TOO_BUSY;
                }
            }
        }
        return (String) body.get("UserId");
    }

    private Map _getUserInfo(String code, int agentId) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("code", code);
        vars.put("agentid", Integer.toString(agentId));

//        ResponseEntity<Map> response = restfulHandler.get(String.valueOf(agentId),
//                ConfigUtils.getUserInfoUrl(),
//                vars
//        );
//        Map body = response.getBody();

        return null;
    }
}
