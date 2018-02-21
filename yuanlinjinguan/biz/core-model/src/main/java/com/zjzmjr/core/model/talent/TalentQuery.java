package com.zjzmjr.core.model.talent;

import com.zjzmjr.core.base.page.BasePageQuery;


public class TalentQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -639686734097145094L;

    private Integer id;
    
    private Integer talentId;

    private Integer companyId;

    private String name;

    private String identityNo;

    private String mobile;

    private Integer recruitmentId;

    private String comeDate;

    private String expectedIncome;
    
    private String qualification;
    
    /**
     * 录入开始时间
     */
    private String createTimeStart;

    /**
     * 录入结束时间
     */
    private String createTimeEnd;

    
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

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getIdentityNo() {
        return identityNo;
    }

    
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    
    public String getMobile() {
        return mobile;
    }

    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    public Integer getRecruitmentId() {
        return recruitmentId;
    }

    
    public void setRecruitmentId(Integer recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    
    public String getComeDate() {
        return comeDate;
    }

    
    public void setComeDate(String comeDate) {
        this.comeDate = comeDate;
    }

    
    public String getExpectedIncome() {
        return expectedIncome;
    }

    
    public void setExpectedIncome(String expectedIncome) {
        this.expectedIncome = expectedIncome;
    }

    
    public String getQualification() {
        return qualification;
    }

    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    
    public String getCreateTimeStart() {
        return createTimeStart;
    }

    
    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    
    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    
    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    
    public Integer getTalentId() {
        return talentId;
    }


    
    public void setTalentId(Integer talentId) {
        this.talentId = talentId;
    }


    @Override
    public String toString() {
        return "TalentQuery [id=" + id + ", talentId=" + talentId + ", companyId=" + companyId + ", name=" + name + ", identityNo=" + identityNo + ", mobile=" + mobile + ", recruitmentId=" + recruitmentId + ", comeDate=" + comeDate + ", expectedIncome=" + expectedIncome + ", qualification=" + qualification + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + "]";
    }
    
    
}
