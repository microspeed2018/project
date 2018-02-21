package com.zjzmjr.core.model.recruitment;

import com.zjzmjr.core.base.page.BasePageQuery;


/**
 * 招聘信息
 * 
 * @author lenovo
 * @version $Id: RecruitmentQuery.java, v 0.1 2017-12-12 下午3:09:09 lenovo Exp $
 */
public class RecruitmentQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -5215711825932590059L;

    private Integer id;
    
    private String positionName;
    
    private String address;
    
    private Integer departmentId;
    
    private String education;
    
    private String salary;
    
    private Integer isValid;
    
    private Integer isOpen;
    
    private String createTimeStart;
    
    private String createTimeEnd;
    
    
    public Integer getId() {
        return id;
    }


    
    public void setId(Integer id) {
        this.id = id;
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

    
    public Integer getDepartmentId() {
        return departmentId;
    }

    
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    
    public String getEducation() {
        return education;
    }

    
    public void setEducation(String education) {
        this.education = education;
    }

    
    public String getSalary() {
        return salary;
    }

    
    public void setSalary(String salary) {
        this.salary = salary;
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
        return "RecruitmentQuery [id=" + id + ", positionName=" + positionName + ", address=" + address + ", departmentId=" + departmentId + ", education=" + education + ", salary=" + salary + ", isValid=" + isValid + ", isOpen=" + isOpen + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + "]";
    }
    
    
}
