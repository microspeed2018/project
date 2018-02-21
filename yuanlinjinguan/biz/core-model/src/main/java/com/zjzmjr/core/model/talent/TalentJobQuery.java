package com.zjzmjr.core.model.talent;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;


/**
 * 人才工作query类
 * 
 * @author lenovo
 * @version $Id: TalentJobQuery.java, v 0.1 2017-12-14 上午10:33:29 lenovo Exp $
 */
public class TalentJobQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -8050115959247071345L;

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


    @Override
    public String toString() {
        return "TalentJobQuery [id=" + id + ", companyId=" + companyId + ", talentId=" + talentId + ", startDate=" + startDate + ", endDate=" + endDate + ", companyName=" + companyName + ", post=" + post + ", leavingReason=" + leavingReason + ", reterence=" + reterence + ", reterenceTel=" + reterenceTel + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + "]";
    }
    
    
}
