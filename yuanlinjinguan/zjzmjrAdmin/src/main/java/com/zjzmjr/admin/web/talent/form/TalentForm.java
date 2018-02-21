package com.zjzmjr.admin.web.talent.form;

import com.zjzmjr.core.common.biz.BaseForm;


public class TalentForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = 1750394018808606186L;

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
    

    //人才附件  
    private String documentName;

    private String documentExplain;

    private String saveName;

    private String saveAddress;
    
    //人才工作
    private String jobStartDate;

    private String jobEndDate;

    private String companyName;

    private String jobPost;

    private String leavingReason;

    private String reterence;

    private String reterenceTel;

    //人才家庭
    private String familyName;

    private String relation;

    private String company;

    private String familyPost;

    private String telephone;

    //人才学历
    private String educationStartDate;

    private String educationEndDate;

    private String schoolName;

    private String professional;

    private String certificate;
    
    private String educationIds;
    
    private String jobIds;
    
    private String familyIds;
    
    private String documentIds;
    
    private String description;

    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
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

    
    public String getDocumentName() {
        return documentName;
    }


    
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }


    
    public String getDocumentExplain() {
        return documentExplain;
    }


    
    public void setDocumentExplain(String documentExplain) {
        this.documentExplain = documentExplain;
    }


    
    public String getSaveName() {
        return saveName;
    }


    
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }


    
    public String getSaveAddress() {
        return saveAddress;
    }


    
    public void setSaveAddress(String saveAddress) {
        this.saveAddress = saveAddress;
    }


    
    public String getJobStartDate() {
        return jobStartDate;
    }


    
    public void setJobStartDate(String jobStartDate) {
        this.jobStartDate = jobStartDate;
    }


    
    public String getJobEndDate() {
        return jobEndDate;
    }


    
    public void setJobEndDate(String jobEndDate) {
        this.jobEndDate = jobEndDate;
    }


    
    public String getCompanyName() {
        return companyName;
    }


    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    
    public String getJobPost() {
        return jobPost;
    }


    
    public void setJobPost(String jobPost) {
        this.jobPost = jobPost;
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


    
    public String getFamilyName() {
        return familyName;
    }


    
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }


    
    public String getRelation() {
        return relation;
    }


    
    public void setRelation(String relation) {
        this.relation = relation;
    }


    
    public String getCompany() {
        return company;
    }


    
    public void setCompany(String company) {
        this.company = company;
    }


    
    public String getFamilyPost() {
        return familyPost;
    }


    
    public void setFamilyPost(String familyPost) {
        this.familyPost = familyPost;
    }


    
    public String getTelephone() {
        return telephone;
    }


    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    
    public String getEducationStartDate() {
        return educationStartDate;
    }


    
    public void setEducationStartDate(String educationStartDate) {
        this.educationStartDate = educationStartDate;
    }


    
    public String getEducationEndDate() {
        return educationEndDate;
    }


    
    public void setEducationEndDate(String educationEndDate) {
        this.educationEndDate = educationEndDate;
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


    
    public String getEducationIds() {
        return educationIds;
    }


    
    public void setEducationIds(String educationIds) {
        this.educationIds = educationIds;
    }


    
    public String getJobIds() {
        return jobIds;
    }


    
    public void setJobIds(String jobIds) {
        this.jobIds = jobIds;
    }


    
    public String getFamilyIds() {
        return familyIds;
    }


    
    public void setFamilyIds(String familyIds) {
        this.familyIds = familyIds;
    }


    
    public String getDocumentIds() {
        return documentIds;
    }


    
    public void setDocumentIds(String documentIds) {
        this.documentIds = documentIds;
    }

    
    public String getDescription() {
        return description;
    }


    
    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "TalentForm [id=" + id + ", talentId=" + talentId + ", companyId=" + companyId + ", name=" + name + ", identityNo=" + identityNo + ", mobile=" + mobile + ", recruitmentId=" + recruitmentId + ", comeDate=" + comeDate + ", expectedIncome=" + expectedIncome + ", qualification=" + qualification + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", documentName=" + documentName + ", documentExplain=" + documentExplain + ", saveName=" + saveName
                + ", saveAddress=" + saveAddress + ", jobStartDate=" + jobStartDate + ", jobEndDate=" + jobEndDate + ", companyName=" + companyName + ", jobPost=" + jobPost + ", leavingReason=" + leavingReason + ", reterence=" + reterence + ", reterenceTel=" + reterenceTel + ", familyName=" + familyName + ", relation=" + relation + ", company=" + company + ", familyPost=" + familyPost + ", telephone=" + telephone + ", educationStartDate=" + educationStartDate + ", educationEndDate="
                + educationEndDate + ", schoolName=" + schoolName + ", professional=" + professional + ", certificate=" + certificate + ", educationIds=" + educationIds + ", jobIds=" + jobIds + ", familyIds=" + familyIds + ", documentIds=" + documentIds + ", description=" + description + "]";
    }
       
}
