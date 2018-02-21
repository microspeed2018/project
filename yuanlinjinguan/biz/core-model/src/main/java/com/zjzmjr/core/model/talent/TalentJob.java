package com.zjzmjr.core.model.talent;

import java.io.Serializable;
import java.util.Date;


/**
 * 人才工作
 * 
 * @author lenovo
 * @version $Id: TalentJob.java, v 0.1 2017-12-13 下午7:57:51 lenovo Exp $
 */
public class TalentJob implements Serializable{

    /**  */
    private static final long serialVersionUID = -5892503737540981263L;
    
    private Integer no;

    private Integer id;

    private Integer companyId;

    private Integer talentId;

    private String startDate;

    private String endDate;

    private String companyName;

    private String post;

    private String leavingReason;

    private String reterence;

    private String reterenceTel;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;
    
    private String talentJobIds;

    private String description;
    
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

    
    public Integer getTalentId() {
        return talentId;
    }

    
    public void setTalentId(Integer talentId) {
        this.talentId = talentId;
    }

    
    public String getStartDate() {
        return startDate;
    }

    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    
    public String getEndDate() {
        return endDate;
    }

    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    
    public String getCompanyName() {
        return companyName;
    }

    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    
    public String getPost() {
        return post;
    }

    
    public void setPost(String post) {
        this.post = post;
    }

    
    public String getLeavingReason() {
        return leavingReason;
    }

    
    public void setLeavingReason(String leavingReason) {
        this.leavingReason = leavingReason;
    }

    
    public String getReterence() {
        return reterence;
    }

    
    public void setReterence(String reterence) {
        this.reterence = reterence;
    }

    
    public String getReterenceTel() {
        return reterenceTel;
    }

    
    public void setReterenceTel(String reterenceTel) {
        this.reterenceTel = reterenceTel;
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

    
    public String getTalentJobIds() {
        return talentJobIds;
    }


    
    public void setTalentJobIds(String talentJobIds) {
        this.talentJobIds = talentJobIds;
    }


    
    public Integer getNo() {
        return no;
    }


    
    public void setNo(Integer no) {
        this.no = no;
    }


    
    public String getDescription() {
        return description;
    }


    
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "TalentJob [no=" + no + ", id=" + id + ", companyId=" + companyId + ", talentId=" + talentId + ", startDate=" + startDate + ", endDate=" + endDate + ", companyName=" + companyName + ", post=" + post + ", leavingReason=" + leavingReason + ", reterence=" + reterence + ", reterenceTel=" + reterenceTel + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", talentJobIds="
                + talentJobIds + ", description=" + description + "]";
    }
    
    

}
