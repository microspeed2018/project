package com.zjzmjr.core.model.user;

import com.zjzmjr.core.model.admin.Admin;

/**
 * 公司员工详细信息
 * 
 * @author oms
 * @version $Id: StaffBasicInfo.java, v 0.1 2017-8-9 下午8:56:34 oms Exp $
 */
public class StaffBasicInfo extends StaffInfo {

    /**  */
    private static final long serialVersionUID = 290916241345572822L;

    private Integer talentId;
    
    private String staffTypeAttribute;
    
    private String educationAttribute;
    
    private String politicalStatusAttribute;
    
    private String jobStatusAttribute;
    
    /**
     * 管理员信息
     */
    private Admin userInfo;

    /** 部门编号 */
    private Integer departId;

    /** 部门名称 */
    private String departmentName;

    /** 职位名称 */
    private String jobName;

    /** 公司名称 */
    private String companyName;

    /** 是否对本人具有可视权限   0 : 没有可视权限   1 : 有可视权限 */
    private Integer viewEnable = 0;

    /**
     * 状态中文说明
     */
    private String statusText;
    
    /**
     * 性别文本
     */
    private String sexText;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 司龄
     */
    private Integer companyAge;
    
    public Admin getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Admin userInfo) {
        this.userInfo = userInfo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public Integer getViewEnable() {
        return viewEnable;
    }

    public void setViewEnable(Integer viewEnable) {
        this.viewEnable = viewEnable;
    }
    
    public String getStatusText() {
        return statusText;
    }

    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    
    public String getStaffTypeAttribute() {
        return staffTypeAttribute;
    }

    
    public void setStaffTypeAttribute(String staffTypeAttribute) {
        this.staffTypeAttribute = staffTypeAttribute;
    }

    
    public String getEducationAttribute() {
        return educationAttribute;
    }

    
    public void setEducationAttribute(String educationAttribute) {
        this.educationAttribute = educationAttribute;
    }

    
    public String getPoliticalStatusAttribute() {
        return politicalStatusAttribute;
    }
    
    public void setPoliticalStatusAttribute(String politicalStatusAttribute) {
        this.politicalStatusAttribute = politicalStatusAttribute;
    }

    public String getJobStatusAttribute() {
        return jobStatusAttribute;
    }
    
    public void setJobStatusAttribute(String jobStatusAttribute) {
        this.jobStatusAttribute = jobStatusAttribute;
    }

    public String getSexText() {
        return sexText;
    }

    
    public void setSexText(String sexText) {
        this.sexText = sexText;
    }

    
    public Integer getAge() {
        return age;
    }

    
    public void setAge(Integer age) {
        this.age = age;
    }

    
    public Integer getCompanyAge() {
        return companyAge;
    }

    
    public void setCompanyAge(Integer companyAge) {
        this.companyAge = companyAge;
    }

    
    public Integer getTalentId() {
        return talentId;
    }

    
    public void setTalentId(Integer talentId) {
        this.talentId = talentId;
    }

    @Override
    public String toString() {
        return "StaffBasicInfo [talentId=" + talentId + ", staffTypeAttribute=" + staffTypeAttribute + ", educationAttribute=" + educationAttribute + ", politicalStatusAttribute=" + politicalStatusAttribute + ", jobStatusAttribute=" + jobStatusAttribute + ", userInfo=" + userInfo + ", departId=" + departId + ", departmentName=" + departmentName + ", jobName=" + jobName + ", companyName=" + companyName + ", viewEnable=" + viewEnable + ", statusText=" + statusText + ", sexText=" + sexText
                + ", age=" + age + ", companyAge=" + companyAge + "]";
    }

}
