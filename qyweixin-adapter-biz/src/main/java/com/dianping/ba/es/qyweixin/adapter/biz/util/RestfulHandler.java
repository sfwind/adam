package com.dianping.ba.es.qyweixin.adapter.biz.util;

import com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken.AccessTokenManager;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yangyuchen on 7/23/14.
 */
@Service
public class RestfulHandler {
    @Autowired
    private AccessTokenManager accessTokenManager;

    private static OkHttpClient client = new OkHttpClient();

    private MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Logger logger = LoggerFactory.getLogger(RestfulHandler.class);

    public String post(String requestUrl, String json) {
        if(StringUtils.isNotEmpty(requestUrl) && StringUtils.isNotEmpty(json)) {
            String accessToken = accessTokenManager.getAccessToken();
            String url = requestUrl.replace("{access_token}", accessToken);
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(JSON, json))
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String body = response.body().string();
                if(CommonUtils.isError(body)){
                    logger.error("execute {} return error, error message is {}", url, body);
                }
                return body;
            } catch (Exception e) {
                logger.error("execute " + requestUrl + " error", e);
            }
        }
        return "";
    }


    public String get(String requestUrl) {
        if(StringUtils.isNotEmpty(requestUrl)) {
            String accessToken = accessTokenManager.getAccessToken();
            String url = requestUrl.replace("{access_token}", accessToken);
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String body = response.body().string();
                if(CommonUtils.isError(body)){
                    logger.error("execute {} return error, error message is {}", url, body);
                }
                return body;
            } catch (Exception e) {
                logger.error("execute " + requestUrl + " error", e);
            }
        }
        return "";
    }

}
