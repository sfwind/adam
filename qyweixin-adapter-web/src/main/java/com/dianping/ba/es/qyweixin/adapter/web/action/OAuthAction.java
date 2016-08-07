package com.dianping.ba.es.qyweixin.adapter.web.action;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.oauth.OAuthManager;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by yangyuchen on 8/14/14.
 */
@RequestMapping("/oauth")
public class OAuthAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthAction.class);
    private static String NOT_FOUND ="notfound";
    private static String EXPIRED = "expired";

    @Autowired
    private OAuthManager oAuthManager;

    private String code;
    private String state;
    private String url;
    private String uuid_token;
    private Integer validTime;
    private int agentId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUuid_token() {
        return uuid_token;
    }

    public void setUuid_token(String uuid_token) {
        this.uuid_token = uuid_token;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    @RequestMapping("/oauthCode")
    public String oauthCode(HttpServletResponse response) {
        if(url!=null) {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("agentId is "+agentId+ "url is "+url);
            }
            String id = oAuthManager.saveCallback(agentId, url, validTime);
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("uuid is "+id);
            }

            String requestUrl = ConfigUtils.getOAuthCodeUrl()
                    .replace("{appid}", ConfigUtils.getCorpId())
                    .replace("{redirect_url}", ConfigUtils.encode(""))
                    .replace("{state}",id);

            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("requestUrl is "+requestUrl);
            }

            try {
                response.sendRedirect(requestUrl);
            } catch (IOException e) {
                LOGGER.error("send info failed", e);
            }
        }
        return null;
    }

    @RequestMapping("/authenticate")
    public String authenticate(HttpServletResponse response) {
        if(state!=null){
            final String id = state;
            String userInfo = oAuthManager.getUserInfo(code, id);
//            Callback callback = oAuthManager.getCallbackParameter(state);
            if(userInfo==null || userInfo.equals(OAuthManager.USER_NO_EXIST)){
                // error handle
//                callback.setCallbackCode(Integer.toString(ErrorConstants.WEIXIN_RETURN_ERROR));
//                userInfo = NOT_FOUND;
                try {
                    response.sendRedirect("/403.html");
                } catch (IOException e) {
                    LOGGER.error("send error info failed", e);
                }
                return null;
            }else if(userInfo.equals(OAuthManager.TOO_BUSY)){
                // error handle
//                callback.setCallbackCode(Integer.toString(ErrorConstants.WEIXIN_RETURN_ERROR));
//                userInfo = NOT_FOUND;
                try {
                    response.sendRedirect("/busy.html");
                } catch (IOException e) {
                    LOGGER.error("send error info failed", e);
                }
                return null;
            }else{
                String url = buildCallbackURL(oAuthManager.getCallbackUrl(id), id, code);
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("url is "+url);
                }

                if(StringUtils.isEmpty(url)){
                    LOGGER.error("build url failed");
                    return null;
                }
                try {
                    response.sendRedirect(url);
                } catch (IOException e) {
                    LOGGER.error("send user info failed", e);
                }
            }
        }
        return null;
    }

    @RequestMapping("/userId")
    public String userId(HttpServletResponse response) throws IOException {
        LOGGER.info("start get userId, uuid token is: " + uuid_token + "  time is: " + new Date().getTime());
        String userid = "";
        int loginId = 0;
        final JsonFactory jsonFactory = new JsonFactory();
        final JsonGenerator jsonGenerator = jsonFactory.createJsonGenerator(response.getWriter());
        response.setContentType("application/json");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("userId", userid);
//        jsonGenerator.writeStringField("loginId", String.valueOf(oAuthManager.getLoginIdByUserId(userId())));
        jsonGenerator.writeEndObject();
        jsonGenerator.close();
        response.flushBuffer();
        LOGGER.info("end get userId, uuid token is: " + uuid_token + "  time is: " + new Date().getTime());
        return null;
    }

    private String buildCallbackURL(String url, String uuid, String code){
        if(StringUtils.isEmpty(url) || StringUtils.isEmpty(uuid) || StringUtils.isEmpty(code)){
            LOGGER.error("input parameter is null");
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        String[] urls = url.split("\\?");

        stringBuilder.append(urls[0]).append("?");

        if(urls.length>1){
            String parameterStr = urls[1];
            StringBuilder encodingString = new StringBuilder();
            String[] params = parameterStr.split("&");
            for(String kVString : params){
                String[] keyValue = kVString.split("=");
                if(keyValue.length > 1)
                    encodingString.append(keyValue[0]).append("=").append(ConfigUtils.encode(keyValue[1])).append("&");
            }
            stringBuilder.append(encodingString);
        }

        return stringBuilder.
                append("uuid_token=").append(uuid).append("&")
                .append("code=").append(code)
                .toString();
    }
}
