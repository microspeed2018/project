package com.zjzmjr.admin.web.user.form;

import java.math.BigDecimal;

import com.zjzmjr.core.common.biz.BaseForm;


public class PersonForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = -5224812914428655142L;

    private Integer id;

    private Integer userId;

    private Integer jobId;

    private Integer jobStatus;
    
    private Integer notJobStatus;

    private String virtualMobile;

    private String telephone;

    private String shortTelephone;

    private String email;

    private String name;

    private Integer personnelNature;

    private String company;

    private String job;

    private String mobile;

    private Integer relatedPerson;
    
    private String relatedPersonName;

    private String memo;

    private Integer status;
    
    private Integer companyId;
    
    private Integer departmentId;
    
    /**
     * 修改时姓名传参
     */
    private String username;
    
    private String createTimeStart;
    
    private String createTimeEnd;
    
    /**
     * 账户状态
     */
    private Integer accStatus;
    
    /**
     * 员工编号
     */
    private Integer employeeNo;
 
    /**
     * 导出在职状态
     */
    private Integer jobStatusSerach;
    
    /**
     * 
     */
    private String orderBy;
    
    private String identityNo;
    
    private Integer sex;
    
    private String birthday;

    private Integer staffType;

    private String entryDate;

    private String entranceGuardCardNumber;

    private Integer politicalStatus;

    private String titleQuality;

    private String graduateInstitutions;

    private String studyMajor;

    private Integer education;

    private String firstWorkDate;

    private String registeredResidence;

    private String presentAddress;

    private String contract1;

    private String contract2;

    private String contract3;

    private String contractDueDate;

    private BigDecimal socialSecurityBase;

    private String bank;

    private String bankcardNum;
    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getUserId() {
        return userId;
    }

    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
    public Integer getJobId() {
        return jobId;
    }

    
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    
    public Integer getJobStatus() {
        return jobStatus;
    }

    
    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getNotJobStatus() {
        return notJobStatus;
    }
    
    public void setNotJobStatus(Integer notJobStatus) {
        this.notJobStatus = notJobStatus;
    }

    public String getVirtualMobile() {
        return virtualMobile;
    }

    
    public void setVirtualMobile(String virtualMobile) {
        this.virtualMobile = virtualMobile;
    }

    
    public String getTelephone() {
        return telephone;
    }

    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    
    public String getShortTelephone() {
        return shortTelephone;
    }

    
    public void setShortTelephone(String shortTelephone) {
        this.shortTelephone = shortTelephone;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public Integer getPersonnelNature() {
        return personnelNature;
    }

    
    public void setPersonnelNature(Integer personnelNature) {
        this.personnelNature = personnelNature;
    }

    
    public String getCompany() {
        return company;
    }

    
    public void setCompany(String company) {
        this.company = company;
    }

    
    public String getJob() {
        return job;
    }

    
    public void setJob(String job) {
        this.job = job;
    }

    
    public String getMobile() {
        return mobile;
    }

    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    
    public Integer getRelatedPerson() {
        return relatedPerson;
    }

    
    public void setRelatedPerson(Integer relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    
    public String getMemo() {
        return memo;
    }

    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }


    
    public Integer getCompanyId() {
        return companyId;
    }


    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }


    
    public Integer getDepartmentId() {
        return departmentId;
    }


    
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    
    
    public String getUsername() {
        return username;
    }


    
    public void setUsername(String username) {
        this.username = username;
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



    public String getRelatedPersonName() {
        return relatedPersonName;
    }


    
    public void setRelatedPersonName(String relatedPersonName) {
        this.relatedPersonName = relatedPersonName;
    }
    
    
    public Integer getJobStatusSerach() {
        return jobStatusSerach;
    }


    
    public void setJobStatusSerach(Integer jobStatusSerach) {
        this.jobStatusSerach = jobStatusSerach;
    }


    
    public String getIdentityNo() {
        return identityNo;
    }


    
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }


    
    public Integer getStaffType() {
        return staffType;
    }


    
    public void setStaffType(Integer staffType) {
        this.staffType = staffType;
    }


    
    public String getEntryDate() {
        return entryDate;
    }


    
    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }


    
    public String getEntranceGuardCardNumber() {
        return entranceGuardCardNumber;
    }


    
    public void setEntranceGuardCardNumber(String entranceGuardCardNumber) {
        this.entranceGuardCardNumber = entranceGuardCardNumber;
    }


    
    public Integer getPoliticalStatus() {
        return politicalStatus;
    }


    
    public void setPoliticalStatus(Integer politicalStatus) {
        this.politicalStatus = politicalStatus;
    }


    
    public String getTitleQuality() {
        return titleQuality;
    }


    
    public void setTitleQuality(String titleQuality) {
        this.titleQuality = titleQuality;
    }


    
    public String getGraduateInstitutions() {
        return graduateInstitutions;
    }


    
    public void setGraduateInstitutions(String graduateInstitutions) {
        this.graduateInstitutions = graduateInstitutions;
    }


    
    public String getStudyMajor() {
        return studyMajor;
    }


    
    public void setStudyMajor(String studyMajor) {
        this.studyMajor = studyMajor;
    }


    
    public Integer getEducation() {
        return education;
    }


    
    public void setEducation(Integer education) {
        this.education = education;
    }


    
    public String getFirstWorkDate() {
        return firstWorkDate;
    }


    
    public void setFirstWorkDate(String firstWorkDate) {
        this.firstWorkDate = firstWorkDate;
    }


    
    public String getRegisteredResidence() {
        return registeredResidence;
    }


    
    public void setRegisteredResidence(String registeredResidence) {
        this.registeredResidence = registeredResidence;
    }


    
    public String getPresentAddress() {
        return presentAddress;
    }


    
    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }


    
    public String getContract1() {
        return contract1;
    }


    
    public void setContract1(String contract1) {
        this.contract1 = contract1;
    }


    
    public String getContract2() {
        return contract2;
    }


    
    public void setContract2(String contract2) {
        this.contract2 = contract2;
    }


    
    public String getContract3() {
        return contract3;
    }


    
    public void setContract3(String contract3) {
        this.contract3 = contract3;
    }


    
    public String getContractDueDate() {
        return contractDueDate;
    }


    
    public void setContractDueDate(String contractDueDate) {
        this.contractDueDate = contractDueDate;
    }


    
    public BigDecimal getSocialSecurityBase() {
        return socialSecurityBase;
    }


    
    public void setSocialSecurityBase(BigDecimal socialSecurityBase) {
        this.socialSecurityBase = socialSecurityBase;
    }


    
    public String getBank() {
        return bank;
    }


    
    public void setBank(String bank) {
        this.bank = bank;
    }


    
    public String getBankcardNum() {
        return bankcardNum;
    }


    
    public void setBankcardNum(String bankcardNum) {
        this.bankcardNum = bankcardNum;
    }

    
    public Integer getSex() {
        return sex;
    }


    
    public void setSex(Integer sex) {
        this.sex = sex;
    }


    
    public String getBirthday() {
        return birthday;
    }


    
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    @Override
    public String toString() {
        return "PersonForm [id=" + id + ", userId=" + userId + ", jobId=" + jobId + ", jobStatus=" + jobStatus + ", notJobStatus=" + notJobStatus + ", virtualMobile=" + virtualMobile + ", telephone=" + telephone + ", shortTelephone=" + shortTelephone + ", email=" + email + ", name=" + name + ", personnelNature=" + personnelNature + ", company=" + company + ", job=" + job + ", mobile=" + mobile + ", relatedPerson=" + relatedPerson + ", relatedPersonName=" + relatedPersonName + ", memo=" + memo
                + ", status=" + status + ", companyId=" + companyId + ", departmentId=" + departmentId + ", username=" + username + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", accStatus=" + accStatus + ", employeeNo=" + employeeNo + ", jobStatusSerach=" + jobStatusSerach + ", orderBy=" + orderBy + ", identityNo=" + identityNo + ", sex=" + sex + ", birthday=" + birthday + ", staffType=" + staffType + ", entryDate=" + entryDate + ", entranceGuardCardNumber="
                + entranceGuardCardNumber + ", politicalStatus=" + politicalStatus + ", titleQuality=" + titleQuality + ", graduateInstitutions=" + graduateInstitutions + ", studyMajor=" + studyMajor + ", education=" + education + ", firstWorkDate=" + firstWorkDate + ", registeredResidence=" + registeredResidence + ", presentAddress=" + presentAddress + ", contract1=" + contract1 + ", contract2=" + contract2 + ", contract3=" + contract3 + ", contractDueDate=" + contractDueDate
                + ", socialSecurityBase=" + socialSecurityBase + ", bank=" + bank + ", bankcardNum=" + bankcardNum + "]";
    }


    public Integer getEmployeeNo() {
        return employeeNo;
    }


    
    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }


    public Integer getAccStatus() {
        return accStatus;
    }


    
    public void setAccStatus(Integer accStatus) {
        this.accStatus = accStatus;
    }

    
    public String getOrderBy() {
        return orderBy;
    }


    
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


    @Override
    public String resolveFiled(String arg0) {
        return null;
    }
    
}
