package com.zjzmjr.finance.web.user.form;

import com.zjzmjr.web.mvc.form.AbstractForm;


public class AdminCreateForm extends AbstractForm {

    /**  */
    private static final long serialVersionUID = -6983860520129495043L;

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
    
    /**
     * 个推推送消息clientId;
     */
    private String clientId;

    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
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

    
    public Integer getJobStatus() {
        return jobStatus;
    }

    
    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
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

    
    public Integer getEmployeeNo() {
        return employeeNo;
    }

    
    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }

    
    public String getClientId() {
        return clientId;
    }


    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    @Override
    public String toString() {
        return "AdminCreateForm [username=" + username + ", name=" + name + ", department=" + department + ", companyId=" + companyId + ", mobile=" + mobile + ", email=" + email + ", password=" + password + ", accStatus=" + accStatus + ", securityIp=" + securityIp + ", telephone=" + telephone + ", jobId=" + jobId + ", jobStatus=" + jobStatus + ", virtualCornet=" + virtualCornet + ", shortTelephone=" + shortTelephone + ", company=" + company + ", job=" + job + ", personnelNature="
                + personnelNature + ", memo=" + memo + ", relatedPerson=" + relatedPerson + ", isEmployee=" + isEmployee + ", employeeNo=" + employeeNo + ", clientId=" + clientId + "]";
    }
    
    
}
