package com.zjzmjr.finance.web.talent.form;

import com.zjzmjr.core.common.biz.BaseForm;


public class TalentForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = -7828703705841111721L;

    private Integer id;
    
    private Integer no;

    private Integer companyId;

    private String name;

    private String identityNo;

    private String mobile;

    private Integer recruitmentId;

    private String comeDate;

    private String expectedIncome;

    private String nation;

    private String origin;

    private Integer maritalStatus;

    private Integer fertilityStatus;

    private String drivingLicenseType;

    private Integer politicalStatus;

    private String qualification;

    private String address;

    private String mail;

    private String emergencyPerson;

    private String emergencyMobile;

    private Integer haveAcquaintance;

    private String acquaintanceName;

    private String acquaintanceDepartment;

    private String acquaintanceRelation;

    private Integer haveCriminal;

    private Integer haveFired;

    private Integer haveDiseases;

    private Integer haveCompetition;

    private String signature;
    
    private Integer talentId;
    
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
    
    private String systemTime;
    
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

    
    public String getNation() {
        return nation;
    }

    
    public void setNation(String nation) {
        this.nation = nation;
    }

    
    public String getOrigin() {
        return origin;
    }

    
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    
    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    
    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    
    public Integer getFertilityStatus() {
        return fertilityStatus;
    }

    
    public void setFertilityStatus(Integer fertilityStatus) {
        this.fertilityStatus = fertilityStatus;
    }

    
    public String getDrivingLicenseType() {
        return drivingLicenseType;
    }

    
    public void setDrivingLicenseType(String drivingLicenseType) {
        this.drivingLicenseType = drivingLicenseType;
    }

    
    public Integer getPoliticalStatus() {
        return politicalStatus;
    }

    
    public void setPoliticalStatus(Integer politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    
    public String getQualification() {
        return qualification;
    }

    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getMail() {
        return mail;
    }

    
    public void setMail(String mail) {
        this.mail = mail;
    }

    
    public String getEmergencyPerson() {
        return emergencyPerson;
    }

    
    public void setEmergencyPerson(String emergencyPerson) {
        this.emergencyPerson = emergencyPerson;
    }

    
    public String getEmergencyMobile() {
        return emergencyMobile;
    }

    
    public void setEmergencyMobile(String emergencyMobile) {
        this.emergencyMobile = emergencyMobile;
    }

    
    public Integer getHaveAcquaintance() {
        return haveAcquaintance;
    }

    
    public void setHaveAcquaintance(Integer haveAcquaintance) {
        this.haveAcquaintance = haveAcquaintance;
    }

    
    public String getAcquaintanceName() {
        return acquaintanceName;
    }

    
    public void setAcquaintanceName(String acquaintanceName) {
        this.acquaintanceName = acquaintanceName;
    }

    
    public String getAcquaintanceDepartment() {
        return acquaintanceDepartment;
    }

    
    public void setAcquaintanceDepartment(String acquaintanceDepartment) {
        this.acquaintanceDepartment = acquaintanceDepartment;
    }

    
    public String getAcquaintanceRelation() {
        return acquaintanceRelation;
    }

    
    public void setAcquaintanceRelation(String acquaintanceRelation) {
        this.acquaintanceRelation = acquaintanceRelation;
    }

    
    public Integer getHaveCriminal() {
        return haveCriminal;
    }

    
    public void setHaveCriminal(Integer haveCriminal) {
        this.haveCriminal = haveCriminal;
    }

    
    public Integer getHaveFired() {
        return haveFired;
    }

    
    public void setHaveFired(Integer haveFired) {
        this.haveFired = haveFired;
    }

    
    public Integer getHaveDiseases() {
        return haveDiseases;
    }

    
    public void setHaveDiseases(Integer haveDiseases) {
        this.haveDiseases = haveDiseases;
    }

    
    public Integer getHaveCompetition() {
        return haveCompetition;
    }

    
    public void setHaveCompetition(Integer haveCompetition) {
        this.haveCompetition = haveCompetition;
    }

    
    public String getSignature() {
        return signature;
    }

    
    public void setSignature(String signature) {
        this.signature = signature;
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

    
    public Integer getTalentId() {
        return talentId;
    }


    
    public void setTalentId(Integer talentId) {
        this.talentId = talentId;
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

    
    public String getSystemTime() {
        return systemTime;
    }


    
    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
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
        return "TalentForm [id=" + id + ", no=" + no + ", companyId=" + companyId + ", name=" + name + ", identityNo=" + identityNo + ", mobile=" + mobile + ", recruitmentId=" + recruitmentId + ", comeDate=" + comeDate + ", expectedIncome=" + expectedIncome + ", nation=" + nation + ", origin=" + origin + ", maritalStatus=" + maritalStatus + ", fertilityStatus=" + fertilityStatus + ", drivingLicenseType=" + drivingLicenseType + ", politicalStatus=" + politicalStatus + ", qualification="
                + qualification + ", address=" + address + ", mail=" + mail + ", emergencyPerson=" + emergencyPerson + ", emergencyMobile=" + emergencyMobile + ", haveAcquaintance=" + haveAcquaintance + ", acquaintanceName=" + acquaintanceName + ", acquaintanceDepartment=" + acquaintanceDepartment + ", acquaintanceRelation=" + acquaintanceRelation + ", haveCriminal=" + haveCriminal + ", haveFired=" + haveFired + ", haveDiseases=" + haveDiseases + ", haveCompetition=" + haveCompetition
                + ", signature=" + signature + ", talentId=" + talentId + ", documentName=" + documentName + ", documentExplain=" + documentExplain + ", saveName=" + saveName + ", saveAddress=" + saveAddress + ", jobStartDate=" + jobStartDate + ", jobEndDate=" + jobEndDate + ", companyName=" + companyName + ", jobPost=" + jobPost + ", leavingReason=" + leavingReason + ", reterence=" + reterence + ", reterenceTel=" + reterenceTel + ", familyName=" + familyName + ", relation=" + relation
                + ", company=" + company + ", familyPost=" + familyPost + ", telephone=" + telephone + ", educationStartDate=" + educationStartDate + ", educationEndDate=" + educationEndDate + ", schoolName=" + schoolName + ", professional=" + professional + ", certificate=" + certificate + ", educationIds=" + educationIds + ", jobIds=" + jobIds + ", familyIds=" + familyIds + ", documentIds=" + documentIds + ", systemTime=" + systemTime + ", description=" + description + "]";
    }
    
    
}
