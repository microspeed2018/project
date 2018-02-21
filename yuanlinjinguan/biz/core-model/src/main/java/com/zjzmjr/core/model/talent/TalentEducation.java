package com.zjzmjr.core.model.talent;

import java.io.Serializable;
import java.util.Date;


/**
 * 人才学历
 * 
 * @author lenovo
 * @version $Id: TalentEducation.java, v 0.1 2017-12-13 下午7:45:25 lenovo Exp $
 */
public class TalentEducation implements Serializable{

    /**  */
    private static final long serialVersionUID = 2834315884754014390L;

    private Integer id;
    
    private Integer no;

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

    private Integer version;

    private String talentEducationIds;
    
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

    
    public Integer getVersion() {
        return version;
    }

    
    public void setVersion(Integer version) {
        this.version = version;
    }

    
    public String getTalentEducationIds() {
        return talentEducationIds;
    }


    
    public void setTalentEducationIds(String talentEducationIds) {
        this.talentEducationIds = talentEducationIds;
    }

    
    public Integer getNo() {
        return no;
    }


    
    public void setNo(Integer no) {
        this.no = no;
    }


    @Override
    public String toString() {
        return "TalentEducation [id=" + id + ", no=" + no + ", companyId=" + companyId + ", talentId=" + talentId + ", startDate=" + startDate + ", endDate=" + endDate + ", schoolName=" + schoolName + ", professional=" + professional + ", certificate=" + certificate + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", talentEducationIds=" + talentEducationIds + "]";
    }
    
    

}
