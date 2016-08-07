package com.dianping.ba.es.qyweixin.adapter.biz.entity.media;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by yangyuchen on 7/22/14.
 */
public class MediaDto implements Serializable{

    public final void fillParameter(Map map){
        injectMap(map);
    }


    protected void injectMap(Map map){
        // override by child class
    }
}
