package com.dianping.ba.es.qyweixin.adapter.biz.entity;

import java.io.Serializable;

/**
 * Created by jason on 15/8/13.
 */
public class OAuthResult implements Serializable{
    private int status;
    private String employeeId;

    public static int EXPIRED = 1;
    public static int NOT_FOUND = 2;
    public static int TOO_BUSY = 3;
    public static int OK = 0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
