package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author oms
 * @version $Id: WorkReport.java, v 0.1 2017-8-10 上午10:25:02 oms Exp $
 */
public class WorkReport implements Serializable {

    /**  */
    private static final long serialVersionUID = -8025127805828494849L;

    private Integer id;

    private Integer userId;

    private Integer projectId;

    private Integer workTypeId;

    private String workContent;

    private String workProof;

    /**
     * 详细地址
     */
    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal precisionGps;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Integer workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent == null ? null : workContent.trim();
    }

    public String getWorkProof() {
        return workProof;
    }

    public void setWorkProof(String workProof) {
        this.workProof = workProof == null ? null : workProof.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getPrecisionGps() {
        return precisionGps;
    }

    public void setPrecisionGps(BigDecimal precisionGps) {
        this.precisionGps = precisionGps;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "WorkReport [id=" + id + ", userId=" + userId + ", projectId=" + projectId + ", workTypeId=" + workTypeId + ", workContent=" + workContent + ", workProof=" + workProof + ", address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + ", precisionGps=" + precisionGps + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}