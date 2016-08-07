package com.dianping.ba.es.qyweixin.adapter.biz.exception;

/**
 * Created by justin on 14-7-22.
 */
public class ErrorConstants {
    // -------------- 错误码 -----------------
    /** 内部错误 */
    public static final int INTERNAL_ERROR = -99;
    /** 微信错误返回 */
    public static final int WEIXIN_RETURN_ERROR = -1;
    /** 没有权限操作 */
    public static final int NO_AUTHORITY = -2;


    // -------------- 错误消息 -----------------
    public static final String INTERNAL_ERROR_MSG = "内部错误，亲，烦请联系系统管理员！";

    public static final String NO_AUTHORITY_MSG = "亲，你不能调用当前方法";
}
