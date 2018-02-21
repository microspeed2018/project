package com.zjzmjr.core.model.user;

public class StaffWagesInfo extends StaffWages {

    private static final long serialVersionUID = -2922453259028159209L;

    /**
     * 员工编号
     */
    private Integer employeeNo;

    /**
     * 状态
     */
    private Integer jobStatus;

    /**
     * 座机
     */
    private String telephone;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门类型(总部/区域)
     */
    private Integer department;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 手机
     */
    private String mobile;

    public Integer getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "StaffWagesInfo [employeeNo=" + employeeNo + ", jobStatus=" + jobStatus + ", telephone=" + telephone + ", name=" + name + ", department=" + department + ", departmentName=" + departmentName + ", mobile=" + mobile + "]";
    }

}
