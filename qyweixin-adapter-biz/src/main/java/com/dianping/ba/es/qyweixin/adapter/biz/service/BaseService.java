package com.dianping.ba.es.qyweixin.adapter.biz.service;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken.AccessTokenService;
import com.dianping.ba.es.qyweixin.adapter.biz.exception.ErrorConstants;
import com.dianping.ba.es.qyweixin.adapter.biz.exception.QyWeixinAdaperException;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by justin on 14-7-22.
 */
public class BaseService {
    protected static Logger logger = LoggerFactory.getLogger(BaseService.class);
    protected static final QyWeixinAdaperException INTERNAL_EXCEPTION = new QyWeixinAdaperException(
            ErrorConstants.INTERNAL_ERROR, ErrorConstants.INTERNAL_ERROR_MSG);
    public static final int ACCESS_TOKEN_EXPIRED = 42001;
    public static final int ACCESS_TOKEN_EXPIRED_NEW = 40014;
    public static final int USER_NO_EXIST = 46004;
    public static final int API_FREQ_OUT_OF_LIMIT = 45009;
    public static final int INVALID_CODE = 40029;
    @Autowired
    private AccessTokenService accessTokenService;

    public static void validateResult(Map result) throws QyWeixinAdaperException {
        int errcode = getCode(result);

        if (errcode != 0) {
            logger.error("errcode=" + errcode + ", errmsg=" + result.get("errmsg") +", errcode="+errcode);
            throw new QyWeixinAdaperException(errcode, (String) result.get("errmsg"));
        }
    }

    public boolean validateCode(String code) {
        if (code == null) {
            return false;
        }
        String authenticationCode = ConfigUtils.getAuthenticationCode();
        if (authenticationCode == null) {
            return true;
        }

        return authenticationCode.equals(code);
    }

    public boolean isAccessTokenExpired(Map result, String agentId) {
        int errcode = getCode(result);
        if (errcode == ACCESS_TOKEN_EXPIRED || errcode == BaseService.ACCESS_TOKEN_EXPIRED_NEW) {
            accessTokenService.refreshAccessToken();
            return true;
        }
        return false;
    }

    private static int getCode(Map result){
        Object code = result.get("errcode");
        if(code==null){
            return 0;
        }
        int errcode = ErrorConstants.WEIXIN_RETURN_ERROR;
        if(code instanceof Integer){
            errcode = (Integer) result.get("errcode");
        }else if(code instanceof String){
            try{
                errcode = Integer.valueOf((String)code);
            }catch (NumberFormatException e){
                errcode = ErrorConstants.WEIXIN_RETURN_ERROR;
            }
        }
        return errcode;
    }

}
