package com.zjzmjr.core.model.admin;

import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.StaffInfo;


public class AdminStaff extends Admin{

    /**  */
    private static final long serialVersionUID = 4469852660530555006L;

    /** 部门编号 */
    private Integer departId;

    /** 部门名称 */
    private String departmentName;

    /** 职位名称 */
    private String jobNames;

    /** 公司名称 */
    private String companyName;
    
    private String personnelNatureName;
    
    private String relatedPersonName;
    
    private StaffInfo staffInfo;
    
    private ExternalPerson externalPerson;

    
    public Integer getDepartId() {
        return departId;
    }

    
    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    
    public String getDepartmentName() {
        return departmentName;
    }

    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    } 
    
    public String getJobNames() {
        return jobNames;
    }


    
    public void setJobNames(String jobNames) {
        this.jobNames = jobNames;
    }


    public String getCompanyName() {
        return companyName;
    }

    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    
    public String getPersonnelNatureName() {
        return personnelNatureName;
    }

    
    public void setPersonnelNatureName(String personnelNatureName) {
        this.personnelNatureName = personnelNatureName;
    }

    
    public String getRelatedPersonName() {
        return relatedPersonName;
    }

    
    public void setRelatedPersonName(String relatedPersonName) {
        this.relatedPersonName = relatedPersonName;
    }

    
    public StaffInfo getStaffInfo() {
        return staffInfo;
    }

    
    public void setStaffInfo(StaffInfo staffInfo) {
        this.staffInfo = staffInfo;
    }

    
    public ExternalPerson getExternalPerson() {
        return externalPerson;
    }

    
    public void setExternalPerson(ExternalPerson externalPerson) {
        this.externalPerson = externalPerson;
    }


    @Override
    public String toString() {
        return "AdminStaff [departId=" + departId + ", departmentName=" + departmentName + ", jobNames=" + jobNames + ", companyName=" + companyName + ", personnelNatureName=" + personnelNatureName + ", relatedPersonName=" + relatedPersonName + ", staffInfo=" + staffInfo + ", externalPerson=" + externalPerson + "]";
    }
    
    
}
