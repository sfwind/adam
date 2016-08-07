package com.dianping.ba.es.qyweixin.adapter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by justin on 5/18/15.
 */
public class SenderThreadPool {

    private ThreadPoolExecutor POOL;

    private static Logger logger = LoggerFactory.getLogger(SenderThreadPool.class);

    private final static int MAX_SIZE = 20;
    private final static int INIT_SIZE = 10;
    private final static int IDLE_TIME = 1;

    public void execute(Thread thread, String threadName) {
        if(thread==null){
            logger.error("thread is null, return at once");
        }
        thread.setName(threadName);

        if(POOL==null){
            init();
        }
        if(!POOL.isTerminating() || !POOL.isTerminated()){
            POOL.execute(thread);
        }else{
            logger.error("pool is terminating, refuse to execute thread any more");
        }
    }

    public void destroy(){
        logger.info("thread pool is destroying");
        if(POOL!=null){
            POOL.shutdown();
            try {
                if (!POOL.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.info("thread pool is terminate now");
                    // pool didn't terminate after the first try
                    POOL.shutdownNow();
                }

                if (!POOL.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.error("thread pool is hanging, leave it alone");
                    // pool didn't terminate after the second try
                }
            } catch (InterruptedException ex) {
                POOL.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void init(){
        logger.info("thread pool is init");
        if(POOL==null){
            POOL = new ThreadPoolExecutor(INIT_SIZE, MAX_SIZE,
                    IDLE_TIME, TimeUnit.MINUTES,
                    new ArrayBlockingQueue<Runnable>(MAX_SIZE, true),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }
    }

}
