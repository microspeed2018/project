package com.zjzmjr.core.model.recruitment;

import java.io.Serializable;
import java.util.Date;


/**
 * 招聘信息
 * 
 * @author lenovo
 * @version $Id: Recruitment.java, v 0.1 2017-12-12 下午1:16:39 lenovo Exp $
 */
public class Recruitment implements Serializable{

    /**  */
    private static final long serialVersionUID = -9058021359207260086L;

    private Integer id;

    private Integer companyId;

    private Integer departmentId;

    private String positionName;

    private String address;

    private String age;

    private String experience;

    private String education;

    private String numbers;

    private String salary;

    private String postDuties;

    private String qualification;

    private Integer isValid;
    
    private Integer isOpen;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    
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

    
    public Integer getDepartmentId() {
        return departmentId;
    }

    
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    
    public String getPositionName() {
        return positionName;
    }

    
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getAge() {
        return age;
    }

    
    public void setAge(String age) {
        this.age = age;
    }

    
    public String getExperience() {
        return experience;
    }

    
    public void setExperience(String experience) {
        this.experience = experience;
    }

    
    public String getEducation() {
        return education;
    }

    
    public void setEducation(String education) {
        this.education = education;
    }

    
    public String getNumbers() {
        return numbers;
    }

    
    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    
    public String getSalary() {
        return salary;
    }

    
    public void setSalary(String salary) {
        this.salary = salary;
    }

    
    public String getPostDuties() {
        return postDuties;
    }

    
    public void setPostDuties(String postDuties) {
        this.postDuties = postDuties;
    }

    
    public String getQualification() {
        return qualification;
    }

    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    
    public Integer getIsValid() {
        return isValid;
    }

    
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    
    public Integer getIsOpen() {
        return isOpen;
    }


    
    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }


    @Override
    public String toString() {
        return "Recruitment [id=" + id + ", companyId=" + companyId + ", departmentId=" + departmentId + ", positionName=" + positionName + ", address=" + address + ", age=" + age + ", experience=" + experience + ", education=" + education + ", numbers=" + numbers + ", salary=" + salary + ", postDuties=" + postDuties + ", qualification=" + qualification + ", isValid=" + isValid + ", isOpen=" + isOpen + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime="
                + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }
    
    
}
