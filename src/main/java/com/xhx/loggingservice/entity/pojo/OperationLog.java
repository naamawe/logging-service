package com.xhx.loggingservice.entity.pojo;

import java.sql.Timestamp;

/**
 * @author master
 */
public class OperationLog {
    private Long logId;
    private Long userId;
    private String action;
    private String ip;
    private String detail;
    private Timestamp gmtCreate;

    public OperationLog() {
    }

    public OperationLog(Long logId, Long userId, String action, String ip, String detail, Timestamp gmtCreate) {
        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.ip = ip;
        this.detail = detail;
        this.gmtCreate = gmtCreate;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return "OperationLog{logId = " + logId + ", userId = " + userId + ", action = " + action +
                ", ip = " + ip + ", detail = " + detail + ", gmtCreate = " + gmtCreate + "}";
    }
}