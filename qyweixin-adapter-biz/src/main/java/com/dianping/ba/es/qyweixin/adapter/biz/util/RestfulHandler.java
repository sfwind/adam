package com.dianping.ba.es.qyweixin.adapter.biz.util;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken.AccessTokenManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by yangyuchen on 7/23/14.
 */
@Service
public class RestfulHandler {
    @Autowired
    private AccessTokenManager accessTokenManager;

    private OkHttpClient client = new OkHttpClient();

    private Logger logger = LoggerFactory.getLogger(RestfulHandler.class);

    public ResponseBody post(String agentId, String requestUrl, Map params) {

        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            logger.error("execute "+requestUrl+" error", e);
        }
        return response.body();
    }


    public ResponseBody get(String agentId, String requestUrl, Map vars) {
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (Exception e) {
            logger.error("execute "+requestUrl+" error", e);
        }
        return response.body();
    }

    public ResponseBody getImage(String agentId, String requestUrl, Map vars) {
//        vars.put("access_token", accessTokenManager.getAccessToken(agentId));
//        ResponseEntity response;
//        try {
//            response = restTemplate.getForEntity(requestUrl, BufferedImage.class, vars);
//        }catch(HttpMessageNotReadableException e){
//            response = restTemplate.getForEntity(requestUrl, Map.class, vars);
//        }
//        return response;
        return null;
    }
}
