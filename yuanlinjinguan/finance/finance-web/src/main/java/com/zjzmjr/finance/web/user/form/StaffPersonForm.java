package com.zjzmjr.finance.web.user.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: StaffPersonForm.java, v 0.1 2017-8-16 下午3:54:05 oms Exp $
 */
public class StaffPersonForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = -5887408904562452847L;

    private String userName;

    private Integer departmentId;

    /**
     * 外部人员属性
     */
    private Integer personnelNature;

    /**
     * 职务
     */
    private Integer jobId;

    /**
     * 在职状态
     */
    private Integer jobStatus;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Integer getPersonnelNature() {
        return personnelNature;
    }

    public void setPersonnelNature(Integer personnelNature) {
        this.personnelNature = personnelNature;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    @Override
    public String toString() {
        return "StaffPersonForm [userName=" + userName + ", departmentId=" + departmentId + ", personnelNature=" + personnelNature + ", jobId=" + jobId + ", jobStatus=" + jobStatus + "]";
    }

}
