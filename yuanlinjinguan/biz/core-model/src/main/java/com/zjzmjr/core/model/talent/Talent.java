package com.zjzmjr.core.model.talent;

import java.io.Serializable;
import java.util.Date;


/**
 * 人才信息
 * 
 * @author lenovo
 * @version $Id: Talent.java, v 0.1 2017-12-13 下午7:39:17 lenovo Exp $
 */
public class Talent implements Serializable{

    /**  */
    private static final long serialVersionUID = 5990173871221574562L;

    private Integer id;

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

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    private String educationIds;
    
    private String jobIds;
    
    private String familyIds;
    
    private String documentIds;
    
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


    @Override
    public String toString() {
        return "Talent [id=" + id + ", companyId=" + companyId + ", name=" + name + ", identityNo=" + identityNo + ", mobile=" + mobile + ", recruitmentId=" + recruitmentId + ", comeDate=" + comeDate + ", expectedIncome=" + expectedIncome + ", nation=" + nation + ", origin=" + origin + ", maritalStatus=" + maritalStatus + ", fertilityStatus=" + fertilityStatus + ", drivingLicenseType=" + drivingLicenseType + ", politicalStatus=" + politicalStatus + ", qualification=" + qualification
                + ", address=" + address + ", mail=" + mail + ", emergencyPerson=" + emergencyPerson + ", emergencyMobile=" + emergencyMobile + ", haveAcquaintance=" + haveAcquaintance + ", acquaintanceName=" + acquaintanceName + ", acquaintanceDepartment=" + acquaintanceDepartment + ", acquaintanceRelation=" + acquaintanceRelation + ", haveCriminal=" + haveCriminal + ", haveFired=" + haveFired + ", haveDiseases=" + haveDiseases + ", haveCompetition=" + haveCompetition + ", signature="
                + signature + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", educationIds=" + educationIds + ", jobIds=" + jobIds + ", familyIds=" + familyIds + ", documentIds=" + documentIds + "]";
    }

}
