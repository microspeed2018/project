package com.zjzmjr.core.model.company;

import com.zjzmjr.core.base.page.BasePageQuery;


public class CompanyDepartmentQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = 1621833166248804320L;

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
     * 职位表部门id
     */
    private String departmentId;
    
    /**
     * 部门状态
     */
    private Integer departmentStatus;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 在职状态
     */
    private Integer jobStatus;
    
    private Integer notJobStatus;
    
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


    
    public String getDepartmentId() {
        return departmentId;
    }


    
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }


    
    public Integer getDepartmentStatus() {
        return departmentStatus;
    }


    
    public void setDepartmentStatus(Integer departmentStatus) {
        this.departmentStatus = departmentStatus;
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

    @Override
    public String toString() {
        return "CompanyDepartmentQuery [id=" + id + ", companyId=" + companyId + ", name=" + name + ", departmentLeader=" + departmentLeader + ", status=" + status + ", departmentId=" + departmentId + ", departmentStatus=" + departmentStatus + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", jobStatus=" + jobStatus + ", notJobStatus=" + notJobStatus + "]";
    }

}
