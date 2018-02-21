/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.form;

import java.math.BigDecimal;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 创建admin用户的Form
 * 
 * @author elliott
 * @version $Id: AdminCreateForm.java, v 0.1 2014-1-15 下午1:25:48 elliott Exp $
 */
public class AdminCreateForm extends AbstractForm {

    private static final long serialVersionUID = -375405087121314767L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门
     */
    private Integer department;

    /**
     * 所属公司编号
     */
    private Integer companyId;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮件
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐户状态
     */
    private Integer accStatus;

    /**
     * 安全IP
     */
    private String securityIp;
    
    /**
     * 座机号码
     */
    private String telephone;
    
    /**
     * 员工职位
     */
    private Integer jobId;
    
    /**
     * 员工在职状态
     */
    private Integer jobStatus;

    /**
     * 虚拟网短号
     */
    private String virtualCornet;
    
    /**
     * 座机短号
     */
    private String shortTelephone;
    
    /**
     * 外部人员公司名称
     */
    private String company;
    
    /**
     * 外部人员职务
     */
    private String job;
    
    /**
     * 外部人员人员性质
     */
    private Integer personnelNature;
    
    /**
     * 备注
     */
    private String memo;
    
    /**
     * 联系人
     */
    private Integer relatedPerson;
    
    /**
     * 是否员工标识
     */
    private Integer isEmployee;
    
    /**
     * 员工编号
     */
    private Integer employeeNo;
    
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

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(Integer accStatus) {
        this.accStatus = accStatus;
    }

    public String getSecurityIp() {
        return securityIp;
    }

    public void setSecurityIp(String securityIp) {
        this.securityIp = securityIp;
    }

    
    public String getTelephone() {
        return telephone;
    }

    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    
    public Integer getJobId() {
        return jobId;
    }

    
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    
    public String getVirtualCornet() {
        return virtualCornet;
    }

    
    public void setVirtualCornet(String virtualCornet) {
        this.virtualCornet = virtualCornet;
    }

    
    public String getShortTelephone() {
        return shortTelephone;
    }

    
    public void setShortTelephone(String shortTelephone) {
        this.shortTelephone = shortTelephone;
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

    
    public Integer getPersonnelNature() {
        return personnelNature;
    }

    
    public void setPersonnelNature(Integer personnelNature) {
        this.personnelNature = personnelNature;
    }

    
    public String getMemo() {
        return memo;
    }

    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    
    public Integer getRelatedPerson() {
        return relatedPerson;
    }

    
    public void setRelatedPerson(Integer relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    
    public Integer getIsEmployee() {
        return isEmployee;
    }

    
    public void setIsEmployee(Integer isEmployee) {
        this.isEmployee = isEmployee;
    }

    
    public Integer getJobStatus() {
        return jobStatus;
    }

    
    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    /**
     * @see com.zjzmjr.web.mvc.form.AbstractForm#resolveFiled(java.lang.String)
     */
    @Override
    public String resolveFiled(String field) {
        if ("username".equals(field)) {
            return "用户名";
        } else if ("name".equals(field)) {
            return "姓名";
        } else if ("department".equals(field)) {
            return "部门";
        } else if ("mobile".equals(field)) {
            return "手机号";
        } else if ("email".equals(field)) {
            return "邮件";
        } else if ("password".equals(field)) {
            return "密码";
        } else if ("accStatus".equals(field)) {
            return "帐户状态";
        } else if ("securityIp".equals(field)) {
            return "安全IP";
        }
        return null;
    }

    
    public Integer getEmployeeNo() {
        return employeeNo;
    }

    
    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
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
        return "AdminCreateForm [username=" + username + ", name=" + name + ", department=" + department + ", companyId=" + companyId + ", mobile=" + mobile + ", email=" + email + ", password=" + password + ", accStatus=" + accStatus + ", securityIp=" + securityIp + ", telephone=" + telephone + ", jobId=" + jobId + ", jobStatus=" + jobStatus + ", virtualCornet=" + virtualCornet + ", shortTelephone=" + shortTelephone + ", company=" + company + ", job=" + job + ", personnelNature="
                + personnelNature + ", memo=" + memo + ", relatedPerson=" + relatedPerson + ", isEmployee=" + isEmployee + ", employeeNo=" + employeeNo + ", identityNo=" + identityNo + ", sex=" + sex + ", birthday=" + birthday + ", staffType=" + staffType + ", entryDate=" + entryDate + ", entranceGuardCardNumber=" + entranceGuardCardNumber + ", politicalStatus=" + politicalStatus + ", titleQuality=" + titleQuality + ", graduateInstitutions=" + graduateInstitutions + ", studyMajor=" + studyMajor
                + ", education=" + education + ", firstWorkDate=" + firstWorkDate + ", registeredResidence=" + registeredResidence + ", presentAddress=" + presentAddress + ", contract1=" + contract1 + ", contract2=" + contract2 + ", contract3=" + contract3 + ", contractDueDate=" + contractDueDate + ", socialSecurityBase=" + socialSecurityBase + ", bank=" + bank + ", bankcardNum=" + bankcardNum + "]";
    }

}
