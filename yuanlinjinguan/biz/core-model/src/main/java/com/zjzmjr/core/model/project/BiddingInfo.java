package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 开标信息表
 * 
 * @author oms
 * @version $Id: BiddingInfo.java, v 0.1 2017-8-23 下午3:48:23 oms Exp $
 */
public class BiddingInfo implements Serializable {

    /**  */
    private static final long serialVersionUID = -8267413169507679844L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private Integer ranking;

    private Integer bidCompany;

    private BigDecimal bidOffer;

    private Integer totleScore;

    private Integer businessScore;

    private Integer technicalScore;

    private Integer otherScore;

    private String projectLeader;

    private String memo;

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getBidCompany() {
        return bidCompany;
    }

    public void setBidCompany(Integer bidCompany) {
        this.bidCompany = bidCompany;
    }

    public BigDecimal getBidOffer() {
        return bidOffer;
    }

    public void setBidOffer(BigDecimal bidOffer) {
        this.bidOffer = bidOffer;
    }

    public Integer getTotleScore() {
        return totleScore;
    }

    public void setTotleScore(Integer totleScore) {
        this.totleScore = totleScore;
    }

    public Integer getBusinessScore() {
        return businessScore;
    }

    public void setBusinessScore(Integer businessScore) {
        this.businessScore = businessScore;
    }

    public Integer getTechnicalScore() {
        return technicalScore;
    }

    public void setTechnicalScore(Integer technicalScore) {
        this.technicalScore = technicalScore;
    }

    public Integer getOtherScore() {
        return otherScore;
    }

    public void setOtherScore(Integer otherScore) {
        this.otherScore = otherScore;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader == null ? null : projectLeader.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
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
        return "BiddingInfo [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", ranking=" + ranking + ", bidCompany=" + bidCompany + ", bidOffer=" + bidOffer + ", totleScore=" + totleScore + ", businessScore=" + businessScore + ", technicalScore=" + technicalScore + ", otherScore=" + otherScore + ", projectLeader=" + projectLeader + ", memo=" + memo + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId="
                + updateUserId + ", version=" + version + "]";
    }

}