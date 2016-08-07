package com.dianping.ba.es.qyweixin.adapter.biz.domain.accessToken;


import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.URLUtil;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class QyWeiXinAccessTokenRepoImpl implements QyWeiXinAccessTokenRepo {
    private OkHttpClient client = new OkHttpClient();

    private Logger logger = LoggerFactory.getLogger(QyWeiXinAccessTokenRepoImpl.class);

    public String getAccessToken() {
        Map<String, String> map = Maps.newHashMap();
        map.put("appid", ConfigUtils.getAppid());
        map.put("secret", ConfigUtils.getSecret());
        String url = URLUtil.urlReplace(ConfigUtils.getAccessTokenUrl(), map);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();

            Map<String, String> gsonMap = new Gson().fromJson(json,
                    new TypeToken<Map<String, String>>() {
                    }.getType());

            if(MapUtils.isNotEmpty(gsonMap)){
                return gsonMap.get("access_token");
            }
        } catch (Exception e) {
            logger.error("execute "+ConfigUtils.getAccessTokenUrl()+" error", e);
        }

        return "";
    }

    public static void main(String[] args) {
        new QyWeiXinAccessTokenRepoImpl().getAccessToken();
    }

}
