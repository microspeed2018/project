package com.zjzmjr.finance.web.recruitment.form;

import com.zjzmjr.web.mvc.form.AbstractForm;


public class RecruitmentForm extends AbstractForm {
    
    /**  */
    private static final long serialVersionUID = 6500282716321074352L;

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
    
    private String createTimeStart;
    
    private String createTimeEnd;

    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
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


    
    public Integer getIsOpen() {
        return isOpen;
    }


    
    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }


    @Override
    public String toString() {
        return "RecruitmentForm [id=" + id + ", companyId=" + companyId + ", departmentId=" + departmentId + ", positionName=" + positionName + ", address=" + address + ", age=" + age + ", experience=" + experience + ", education=" + education + ", numbers=" + numbers + ", salary=" + salary + ", postDuties=" + postDuties + ", qualification=" + qualification + ", isValid=" + isValid + ", isOpen=" + isOpen + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + "]";
    }
    
    
}
