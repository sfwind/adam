package com.dianping.ba.es.qyweixin.adapter.biz.entity.media;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyuchen on 7/22/14.
 */
public class TextDto extends MediaDto{
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected void injectMap(Map params){
        if(params==null){
            params = new HashMap();
        }
        params.put("msgtype", "text");
        Map content = new HashMap();
        content.put("content", this.getContent());
        params.put("text", content);
    }

    @Override
    public String toString() {
        return "{content="+content+",msgtype=text}";
    }
}
