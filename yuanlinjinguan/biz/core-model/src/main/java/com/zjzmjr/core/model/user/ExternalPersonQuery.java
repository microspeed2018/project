package com.zjzmjr.core.model.user;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 外部员工查询条件类
 * 
 * @author oms
 * @version $Id: ExternalPersonQuery.java, v 0.1 2017-8-15 下午6:01:03 oms Exp $
 */
public class ExternalPersonQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = -1808459932957101727L;

    private Integer id;
    
    private String name;
    
    private Integer userId;

    private Integer personnelNature;

    private String company;

    private String mobile;

    private String email;

    private Integer relatedPerson;
    
    private String relatedPersonName;
    
    private Integer status;

    private String createTimeStart;

    private String createTimeEnd;
    
    /**
     * 登陆公司id
     */
    private Integer companyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPersonnelNature() {
        return personnelNature;
    }

    public void setPersonnelNature(Integer personnelNature) {
        this.personnelNature = personnelNature;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public Integer getRelatedPerson() {
        return relatedPerson;
    }

    public void setRelatedPerson(Integer relatedPerson) {
        this.relatedPerson = relatedPerson;
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

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getUserId() {
        return userId;
    }

    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
    public String getRelatedPersonName() {
        return relatedPersonName;
    }

    
    public void setRelatedPersonName(String relatedPersonName) {
        this.relatedPersonName = relatedPersonName;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getCompanyId() {
        return companyId;
    }

    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "ExternalPersonQuery [id=" + id + ", name=" + name + ", userId=" + userId + ", personnelNature=" + personnelNature + ", company=" + company + ", mobile=" + mobile + ", email=" + email + ", relatedPerson=" + relatedPerson + ", relatedPersonName=" + relatedPersonName + ", status=" + status + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", companyId=" + companyId + "]";
    }


}
