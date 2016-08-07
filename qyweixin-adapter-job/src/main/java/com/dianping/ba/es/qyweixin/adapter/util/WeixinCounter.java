package com.dianping.ba.es.qyweixin.adapter.util;

import com.dianping.ba.es.qyweixin.adapter.biz.util.DateUtil;
import com.dianping.ba.es.qyweixin.adapter.biz.util.LionUtils;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by justin on 1/16/15.
 */
public class WeixinCounter {
    private static AtomicLong COUNTER = new AtomicLong();

    private static volatile int minute = 0;

    private static Object lock = new Object();

    public static boolean exhaust() {
        if (COUNTER.get() >= LionUtils.getWeixinThreshold()) {
            return true;
        }

        return false;
    }

    public static void invoke(Integer m) {
        // 判断是否在同一分钟
        if(minute!=m){
            minute = m;
            clear();
            COUNTER.incrementAndGet();
            return;
        }
        long count = COUNTER.incrementAndGet();
        if (exhaust()) {
            synchronized (lock) {
                if (exhaust()) {
                    try {
                        Thread.sleep(1000 * 60);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                    minute = DateUtil.getMinuteByDate(new Date());
                    clear();
                }
            }
        }
    }

    public static void clear() {
        COUNTER.set(0);
    }
}
