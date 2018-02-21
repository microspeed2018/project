package com.zjzmjr.core.model.talent;

import java.io.Serializable;
import java.util.Date;


/**
 * 人才附件
 * 
 * @author lenovo
 * @version $Id: TalentDocument.java, v 0.1 2017-12-13 下午7:58:50 lenovo Exp $
 */
public class TalentDocument implements Serializable{

    /**  */
    private static final long serialVersionUID = 1137535332819063079L;

    private Integer id;
    
    private Integer no;

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

    private Integer version;

    private String talentDocumentIds;
    
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

    
    public Integer getVersion() {
        return version;
    }

    
    public void setVersion(Integer version) {
        this.version = version;
    }

    
    public String getTalentDocumentIds() {
        return talentDocumentIds;
    }


    
    public void setTalentDocumentIds(String talentDocumentIds) {
        this.talentDocumentIds = talentDocumentIds;
    }

    
    public Integer getNo() {
        return no;
    }


    
    public void setNo(Integer no) {
        this.no = no;
    }


    @Override
    public String toString() {
        return "TalentDocument [id=" + id + ", no=" + no + ", companyId=" + companyId + ", talentId=" + talentId + ", documentName=" + documentName + ", documentExplain=" + documentExplain + ", saveName=" + saveName + ", saveAddress=" + saveAddress + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", talentDocumentIds=" + talentDocumentIds + "]";
    }
    
    
}
