package com.zjzmjr.core.model.talent;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;


/**
 * 人才学历 query
 * 
 * @author lenovo
 * @version $Id: TalentEducationQuery.java, v 0.1 2017-12-14 上午11:04:00 lenovo Exp $
 */
public class TalentEducationQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -2116522784411703010L;

    private Integer id;

    private Integer companyId;

    private Integer talentId;

    private String startDate;

    private String endDate;

    private String schoolName;

    private String professional;

    private String certificate;

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

    
    public String getSchoolName() {
        return schoolName;
    }

    
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    
    public String getProfessional() {
        return professional;
    }

    
    public void setProfessional(String professional) {
        this.professional = professional;
    }

    
    public String getCertificate() {
        return certificate;
    }

    
    public void setCertificate(String certificate) {
        this.certificate = certificate;
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
        return "TalentEducationQuery [id=" + id + ", companyId=" + companyId + ", talentId=" + talentId + ", startDate=" + startDate + ", endDate=" + endDate + ", schoolName=" + schoolName + ", professional=" + professional + ", certificate=" + certificate + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + "]";
    }
    
    
}
