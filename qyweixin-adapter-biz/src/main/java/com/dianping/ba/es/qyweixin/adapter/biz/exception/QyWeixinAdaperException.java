package com.dianping.ba.es.qyweixin.adapter.biz.exception;

/**
 * Created by yangyuchen on 7/22/14.
 */
public class QyWeixinAdaperException extends Exception {
    private String errMsg;
    private int errcode;

    public QyWeixinAdaperException(int errcode, String errMsg) {
        this.errMsg = errMsg;
        this.errcode = errcode;
    }

    public QyWeixinAdaperException(String message, String errMsg, int errcode) {
        super(message);
        this.errMsg = errMsg;
        this.errcode = errcode;
    }

    public QyWeixinAdaperException(String message, Throwable throwable, String errMsg, int errcode) {
        super(message, throwable);
        this.errMsg = errMsg;
        this.errcode = errcode;
    }

    public QyWeixinAdaperException(Throwable throwable, String errMsg, int errcode) {
        super(throwable);
        this.errMsg = errMsg;
        this.errcode = errcode;
    }

    @Override
    public String getMessage(){
        return errMsg;
    }

    public int getErrcode() {
        return errcode;
    }
}
