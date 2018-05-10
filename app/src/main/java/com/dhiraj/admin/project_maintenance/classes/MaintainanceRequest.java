package com.dhiraj.admin.project_maintenance.classes;

/**
 * Created by admin on 5/9/18.
 */

public class MaintainanceRequest {
    String reqId, type, details, createTime, endTime, actualEndTIme,status;

    public MaintainanceRequest(String reqId, String type, String details, String createTime, String endTime, String actualEndTIme, String status) {
        this.reqId = reqId;
        this.type = type;
        this.details = details;
        this.createTime = createTime;
        this.endTime = endTime;
        this.actualEndTIme = actualEndTIme;
        this.status = status;
    }

    public MaintainanceRequest(){

    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getActualEndTIme() {
        return actualEndTIme;
    }

    public void setActualEndTIme(String actualEndTIme) {
        this.actualEndTIme = actualEndTIme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
