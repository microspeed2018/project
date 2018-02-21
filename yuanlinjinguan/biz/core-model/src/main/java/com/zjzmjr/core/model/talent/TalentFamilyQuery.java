package com.zjzmjr.core.model.talent;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;


/**
 * 人才家属 query
 * 
 * @author lenovo
 * @version $Id: TalentFamilyQuery.java, v 0.1 2017-12-14 上午11:05:05 lenovo Exp $
 */
public class TalentFamilyQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -2356505771636839787L;

    private Integer id;

    private Integer companyId;

    private Integer talentId;

    private String name;

    private String relation;

    private String company;

    private String post;

    private String telephone;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    
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

    
    public Integer getTalentId() {
        return talentId;
    }

    
    public void setTalentId(Integer talentId) {
        this.talentId = talentId;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getRelation() {
        return relation;
    }

    
    public void setRelation(String relation) {
        this.relation = relation;
    }

    
    public String getCompany() {
        return company;
    }

    
    public void setCompany(String company) {
        this.company = company;
    }

    
    public String getPost() {
        return post;
    }

    
    public void setPost(String post) {
        this.post = post;
    }

    
    public String getTelephone() {
        return telephone;
    }

    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
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


    @Override
    public String toString() {
        return "TalentFamilyQuery [id=" + id + ", companyId=" + companyId + ", talentId=" + talentId + ", name=" + name + ", relation=" + relation + ", company=" + company + ", post=" + post + ", telephone=" + telephone + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + "]";
    }
    
    
}
