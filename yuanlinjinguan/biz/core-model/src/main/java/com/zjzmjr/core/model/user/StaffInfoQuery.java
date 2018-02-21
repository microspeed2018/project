package com.zjzmjr.core.model.user;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 员工信息查询
 * 
 * @author oms
 * @version $Id: StaffInfoQuery.java, v 0.1 2017-8-9 下午3:20:44 oms Exp $
 */
public class StaffInfoQuery extends BasePageQuery {

	/**  */
	private static final long serialVersionUID = 6613974875652462417L;

	private Integer id;

	private Integer userId;

	private String userName;

	private Integer companyId;

	private Integer departmentId;

	/**
	 * 职务编号
	 */
	private Integer jobId;

	/**
	 * 除了该职务编号以外的数据
	 */
	private Integer notJobId;

	private Integer jobStatus;
	
	/**
     * 除了该状态以外的数据
     */
	private Integer notJobStatus;

	private String mobile;

	private String createTimeStart;

	private String createTimeEnd;
	
	/**
	 * 管理端姓名模糊匹配
	 */
	private String name;
	
	private String telephone;
	
	private Integer employeeNo;

	/**
	 * 合同到期提前通知时间（单位：月）
	 */
	private Integer contractDueInform;
	
	/**
	 * 生日提前通知时间（单位：月）
	 */
	private Integer birthdayInform;
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Integer getNotJobId() {
		return notJobId;
	}

	public void setNotJobId(Integer notJobId) {
		this.notJobId = notJobId;
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

    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getTelephone() {
        return telephone;
    }

    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Integer getEmployeeNo() {
        return employeeNo;
    }

    
    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }
    
    public Integer getContractDueInform() {
        return contractDueInform;
    }
    
    public void setContractDueInform(Integer contractDueInform) {
        this.contractDueInform = contractDueInform;
    }
    
    public Integer getBirthdayInform() {
        return birthdayInform;
    }
    
    public void setBirthdayInform(Integer birthdayInform) {
        this.birthdayInform = birthdayInform;
    }

    @Override
    public String toString() {
        return "StaffInfoQuery [id=" + id + ", userId=" + userId + ", userName=" + userName + ", companyId=" + companyId + ", departmentId=" + departmentId + ", jobId=" + jobId + ", notJobId=" + notJobId + ", jobStatus=" + jobStatus + ", notJobStatus=" + notJobStatus + ", mobile=" + mobile + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", name=" + name + ", telephone=" + telephone + ", employeeNo=" + employeeNo + ", contractDueInform=" + contractDueInform
                + ", birthdayInform=" + birthdayInform + "]";
    }


}
