package com.dianping.ba.es.qyweixin.adapter.util;


import com.dianping.ba.es.qyweixin.adapter.biz.entity.MessageDto;

/**
 * Created by justin on 9/14/15.
 */
public class SenderThreadPoolFactory {
    private static SenderThreadPool NORMAL = new SenderThreadPool();
    private static SenderThreadPool URGENT = new SenderThreadPool();

    public static SenderThreadPool create(int priority){
        if(priority == MessageDto.SINGLE_MSG){
            return URGENT;
        }
        return NORMAL;
    }
}
