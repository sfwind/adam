package com.dianping.ba.es.qyweixin.adapter.biz.entity.media;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qianyuhang on 11/19/15.
 */
public class MpNewsDto extends MediaDto implements Serializable{
    private String media_id;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    @Override
    protected void injectMap(Map params){
        if(params==null){
            params = new HashMap();
        }
        params.put("msgtype", "mpnews");
        Map content = new HashMap();
        content.put("media_id", this.getMedia_id());
        params.put("mpnews", content);
    }

    @Override
    public String toString() {
        return "{media_id="+media_id+",msgtype=mpnews}";
    }
}
