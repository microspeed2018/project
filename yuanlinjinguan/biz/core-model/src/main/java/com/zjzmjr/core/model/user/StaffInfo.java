package com.zjzmjr.core.model.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工信息
 * 
 * @author oms
 * @version $Id: StaffInfo.java, v 0.1 2017-8-9 下午2:21:11 oms Exp $
 */
public class StaffInfo implements Serializable {

	/**  */
	private static final long serialVersionUID = -1015590416920328646L;

    private Integer id;

    private Integer userId;

    private Integer jobStatus;

    private String virtualMobile;

    private String telephone;

    private String shortTelephone;

    private String email;

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
    
    private String memo;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;
	
	/**
	 * 员工编号
	 */
	private Integer employeeNo;

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

	public Integer getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getVirtualMobile() {
		return virtualMobile;
	}

	public void setVirtualMobile(String virtualMobile) {
		this.virtualMobile = virtualMobile == null ? null : virtualMobile
				.trim();
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public String getShortTelephone() {
		return shortTelephone;
	}

	public void setShortTelephone(String shortTelephone) {
		this.shortTelephone = shortTelephone == null ? null : shortTelephone
				.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
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
    
    public String getMemo() {
        return memo;
    }

    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "StaffInfo [id=" + id + ", userId=" + userId + ", jobStatus=" + jobStatus + ", virtualMobile=" + virtualMobile + ", telephone=" + telephone + ", shortTelephone=" + shortTelephone + ", email=" + email + ", identityNo=" + identityNo + ", sex=" + sex + ", birthday=" + birthday + ", staffType=" + staffType + ", entryDate=" + entryDate + ", entranceGuardCardNumber=" + entranceGuardCardNumber + ", politicalStatus=" + politicalStatus + ", titleQuality=" + titleQuality
                + ", graduateInstitutions=" + graduateInstitutions + ", studyMajor=" + studyMajor + ", education=" + education + ", firstWorkDate=" + firstWorkDate + ", registeredResidence=" + registeredResidence + ", presentAddress=" + presentAddress + ", contract1=" + contract1 + ", contract2=" + contract2 + ", contract3=" + contract3 + ", contractDueDate=" + contractDueDate + ", socialSecurityBase=" + socialSecurityBase + ", bank=" + bank + ", bankcardNum=" + bankcardNum + ", memo=" + memo
                + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", employeeNo=" + employeeNo + "]";
    }
    

}