package com.zjzmjr.core.model.talent;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;


/**
 * 人才附件query
 * 
 * @author lenovo
 * @version $Id: TalentDocumentQuery.java, v 0.1 2017-12-14 上午10:54:54 lenovo Exp $
 */
public class TalentDocumentQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -1403269344875499344L;

    private Integer id;

    private Integer companyId;

    private Integer talentId;

    private String documentName;

    private String documentExplain;

    private String saveName;

    private String saveAddress;

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

    
    public String getDocumentName() {
        return documentName;
    }

    
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    
    public String getDocumentExplain() {
        return documentExplain;
    }

    
    public void setDocumentExplain(String documentExplain) {
        this.documentExplain = documentExplain;
    }

    
    public String getSaveName() {
        return saveName;
    }

    
    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    
    public String getSaveAddress() {
        return saveAddress;
    }

    
    public void setSaveAddress(String saveAddress) {
        this.saveAddress = saveAddress;
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
        return "TalentDocumentQuery [id=" + id + ", companyId=" + companyId + ", talentId=" + talentId + ", documentName=" + documentName + ", documentExplain=" + documentExplain + ", saveName=" + saveName + ", saveAddress=" + saveAddress + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + "]";
    }
    
    
}
