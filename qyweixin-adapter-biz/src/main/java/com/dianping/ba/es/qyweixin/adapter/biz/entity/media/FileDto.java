package com.dianping.ba.es.qyweixin.adapter.biz.entity.media;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyuchen on 7/22/14.
 */
public class FileDto extends MediaDto{
    private int media_id;

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    @Override
    protected void injectMap(Map params){
        if(params==null){
            params = new HashMap();
        }
        params.put("msgtype", "file");
        Map content = new HashMap();
        content.put("media_id", this.getMedia_id());
        params.put("file", content);
    }

    @Override
    public String toString() {
        return "{media_id="+media_id+",msgtype=file}";
    }
}
