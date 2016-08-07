package com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccessTokenManagerImpl implements AccessTokenManager {
    private static String accessToken;
    protected static Logger logger = LoggerFactory.getLogger(AccessTokenManagerImpl.class);
    @Autowired
    private QyWeiXinAccessTokenRepo qyWeiXinAccessTokenRepo;

    public synchronized String getAccessToken() {
        if(accessToken!=null){
            return accessToken;
        }

        return _getAccessToken();
    }

    private String _getAccessToken() {
        logger.info("refreshing access token");
        String strAccessToken = qyWeiXinAccessTokenRepo.getAccessToken();
        if(strAccessToken!=null){
            accessToken = strAccessToken;
        }
        return accessToken;
    }

    public synchronized String refreshAccessToken() {
        return _getAccessToken();
    }
}
