package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司资质表
 * 
 * @author oms
 * @version $Id: CompanyAptitude.java, v 0.1 2017-8-11 上午9:49:08 oms Exp $
 */
public class CompanyAptitude implements Serializable {

    /**  */
    private static final long serialVersionUID = -7248281635087349188L;

    private Integer id;

    private Integer companyId;

    private Integer aptitudeId;

    private Integer gradeId;

    private String certificateNo;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAptitudeId() {
        return aptitudeId;
    }

    public void setAptitudeId(Integer aptitudeId) {
        this.aptitudeId = aptitudeId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
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
        return "CompanyAptitude [id=" + id + ", companyId=" + companyId + ", aptitudeId=" + aptitudeId + ", gradeId=" + gradeId + ", certificateNo=" + certificateNo + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}