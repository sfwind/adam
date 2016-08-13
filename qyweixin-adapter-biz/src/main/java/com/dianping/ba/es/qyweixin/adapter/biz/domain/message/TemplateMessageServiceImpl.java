package com.dianping.ba.es.qyweixin.adapter.biz.domain.message;

import com.dianping.ba.es.qyweixin.adapter.biz.util.CommonUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.ConfigUtils;
import com.dianping.ba.es.qyweixin.adapter.biz.util.RestfulHandler;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by justin on 16/8/10.
 */
@Service
public class TemplateMessageServiceImpl implements TemplateMessageService {
    @Autowired
    private RestfulHandler restfulHandler;

    public boolean sendMessage(TemplateMessage templateMessage) {
        String url = SEND_MESSAGE_URL;
        String json = new Gson().toJson(templateMessage);
        String body = restfulHandler.post(url, json);
        // TODO 记录messageid
        return !CommonUtils.isError(body);
    }

    public String getTemplateId(String templateShortId) {
        Map<String, String> map = Maps.newHashMap();
        map.put("template_id_short", templateShortId);

        String url = SEND_MESSAGE_URL;
        String json = new Gson().toJson(map);
        String body = restfulHandler.post(url, json);
        if(CommonUtils.isError(body)){
            return "";
        }
        Map<String, Object> response = CommonUtils.jsonToMap(body);
        return (String)response.get("template_id");
    }
}
