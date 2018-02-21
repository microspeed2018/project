package com.zjzmjr.admin.web.user.form;

import org.springframework.web.multipart.MultipartFile;

import com.zjzmjr.core.common.biz.BaseForm;

public class StaffWagesForm extends BaseForm {

    private static final long serialVersionUID = 3268956649602961412L;

    private Integer id;

    /**
     * 所属公司
     */
    private Integer companyId;

    /**
     * 员工编号
     */
    private Integer staffInfoId;

    /**
     * 薪酬月份
     */
    private Integer wagesMonth;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 姓名
     */
    private String name;

    /**
     * 所属部门
     */
    private Integer departmentId;

    /**
     * 员工座机
     */
    private String telephone;

    /**
     * 员工状态
     */
    private Integer status;

    /**
     * 录入开始时间
     */
    private String startDate;

    /**
     * 录入结束时间
     */
    private String endDate;

    /**
     * 员工编号
     */
    private Integer employeeNo;

    private MultipartFile excelAddress;

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

    public Integer getStaffInfoId() {
        return staffInfoId;
    }

    public void setStaffInfoId(Integer staffInfoId) {
        this.staffInfoId = staffInfoId;
    }

    public Integer getWagesMonth() {
        return wagesMonth;
    }

    public void setWagesMonth(Integer wagesMonth) {
        this.wagesMonth = wagesMonth;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }

    public MultipartFile getExcelAddress() {
        return excelAddress;
    }

    public void setExcelAddress(MultipartFile excelAddress) {
        this.excelAddress = excelAddress;
    }

    @Override
    public String toString() {
        return "StaffWagesForm [id=" + id + ", companyId=" + companyId + ", staffInfoId=" + staffInfoId + ", wagesMonth=" + wagesMonth + ", mobile=" + mobile + ", name=" + name + ", departmentId=" + departmentId + ", telephone=" + telephone + ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", employeeNo=" + employeeNo + ", excelAddress=" + excelAddress + "]";
    }

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

}
