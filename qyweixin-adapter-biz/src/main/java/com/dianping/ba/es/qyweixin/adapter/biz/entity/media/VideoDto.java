package com.dianping.ba.es.qyweixin.adapter.biz.entity.media;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangyuchen on 7/22/14.
 */
public class VideoDto extends MediaDto{
    private int media_id;
    private String title;
    private String description;

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected void injectMap(Map params){
        if(params==null){
            params = new HashMap();
        }
        params.put("msgtype", "video");
        Map content = new HashMap();
        content.put("media_id", this.getMedia_id());
        content.put("title", this.getTitle());
        content.put("description", this.getDescription());
        params.put("video", content);
    }

    @Override
    public String toString() {
        return "{media_id="+media_id+",title="+title+",msgtype=video}";
    }
}
