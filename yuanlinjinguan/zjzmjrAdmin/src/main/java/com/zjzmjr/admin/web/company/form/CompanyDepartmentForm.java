package com.zjzmjr.admin.web.company.form;

import com.zjzmjr.core.common.biz.BaseForm;

public class CompanyDepartmentForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = 6770264372371269812L;

    private Integer id;

    /**
     * 所属公司
     */
    private Integer companyId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门负责人
     */
    private Integer departmentLeader;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 所属部门
     */
    private String departmentId;

    /**
     * 职位名称
     */
    private String jobName;

    private String createTimeStart;

    private String createTimeEnd;

    private Integer type;

    @Override
    public String resolveFiled(String arg0) {
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

    public Integer getDepartmentLeader() {
        return departmentLeader;
    }

    public void setDepartmentLeader(Integer departmentLeader) {
        this.departmentLeader = departmentLeader;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CompanyDepartmentForm [id=" + id + ", companyId=" + companyId + ", name=" + name + ", departmentLeader=" + departmentLeader + ", status=" + status + ", departmentId=" + departmentId + ", jobName=" + jobName + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", type=" + type + "]";
    }

}
