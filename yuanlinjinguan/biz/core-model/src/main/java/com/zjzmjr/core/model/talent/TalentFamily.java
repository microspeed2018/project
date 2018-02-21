package com.zjzmjr.core.model.talent;

import java.io.Serializable;
import java.util.Date;


/**
 * 人才家属
 * 
 * @author lenovo
 * @version $Id: TalentFamily.java, v 0.1 2017-12-13 下午7:53:06 lenovo Exp $
 */
public class TalentFamily implements Serializable{

    /**  */
    private static final long serialVersionUID = -9172832260629838556L;

    private Integer id;
    
    private Integer no;

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

    private Integer version;
    
    private String talentFamilyIds;

    
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

    
    public Integer getVersion() {
        return version;
    }

    
    public void setVersion(Integer version) {
        this.version = version;
    }

    
    public String getTalentFamilyIds() {
        return talentFamilyIds;
    }


    
    public void setTalentFamilyIds(String talentFamilyIds) {
        this.talentFamilyIds = talentFamilyIds;
    }

    
    public Integer getNo() {
        return no;
    }


    
    public void setNo(Integer no) {
        this.no = no;
    }


    @Override
    public String toString() {
        return "TalentFamily [id=" + id + ", no=" + no + ", companyId=" + companyId + ", talentId=" + talentId + ", name=" + name + ", relation=" + relation + ", company=" + company + ", post=" + post + ", telephone=" + telephone + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", talentFamilyIds=" + talentFamilyIds + "]";
    }
    
    

}
