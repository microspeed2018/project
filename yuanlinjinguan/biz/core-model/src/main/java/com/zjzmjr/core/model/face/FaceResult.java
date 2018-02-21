package com.zjzmjr.core.model.face;

import java.io.Serializable;


public class FaceResult implements Serializable{

    /**  */
    private static final long serialVersionUID = 2166054588890593258L;

    private String status;
    
    private String livenessResult;
    
    /**
     * 活体采集人像和数据源照片对比结果
     */
    private String faceidConfidence;
    
    private String verifyErrorMessage;
    
    private String isAttacked;

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getLivenessResult() {
        return livenessResult;
    }

    
    public void setLivenessResult(String livenessResult) {
        this.livenessResult = livenessResult;
    }

    
    public String getFaceidConfidence() {
        return faceidConfidence;
    }

    
    public void setFaceidConfidence(String faceidConfidence) {
        this.faceidConfidence = faceidConfidence;
    }

    
    public String getVerifyErrorMessage() {
        return verifyErrorMessage;
    }

    
    public void setVerifyErrorMessage(String verifyErrorMessage) {
        this.verifyErrorMessage = verifyErrorMessage;
    }

    
    public String getIsAttacked() {
        return isAttacked;
    }

    
    public void setIsAttacked(String isAttacked) {
        this.isAttacked = isAttacked;
    }


    @Override
    public String toString() {
        return "FaceResult [status=" + status + ", livenessResult=" + livenessResult + ", faceidConfidence=" + faceidConfidence + ", verifyErrorMessage=" + verifyErrorMessage + ", isAttacked=" + isAttacked + "]";
    }
    
    
}
