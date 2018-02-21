package com.zjzmjr.core.model.file;

import java.io.Serializable;
import java.util.Date;

/**
 * 共享文件表t_shared_file
 * 
 * @author Administrator
 * @version $Id: SharedFile.java, v 0.1 2017-12-12 上午10:30:34 Administrator Exp $
 */
public class SharedFile implements Serializable {

    private static final long serialVersionUID = 1637568240538909156L;

    private Integer id;

    /**
     * 所属公司
     */
    private Integer companyId;

    /**
     * 上传人
     */
    private Integer sharedStaff;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件说明
     */
    private String fileExplain;

    /**
     * 保存名称
     */
    private String saveName;

    /**
     * 保存地址
     */
    private String saveAddress;

    /**
     * 上传方式（1:客户端 2:后台管理系统）默认2
     */
    private Integer sourceType;

    /**
     * 消息提醒（0:否 1:是）默认0
     */
    private Integer isMessage;

    /**
     * 短信提醒（0:否 1:是）默认0
     */
    private Integer isSms;

    /**
     * 是否置顶（0:否 1:是）默认0
     */
    private Integer isTop;

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

    public Integer getSharedStaff() {
        return sharedStaff;
    }

    public void setSharedStaff(Integer sharedStaff) {
        this.sharedStaff = sharedStaff;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileExplain() {
        return fileExplain;
    }

    public void setFileExplain(String fileExplain) {
        this.fileExplain = fileExplain == null ? null : fileExplain.trim();
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName == null ? null : saveName.trim();
    }

    public String getSaveAddress() {
        return saveAddress;
    }

    public void setSaveAddress(String saveAddress) {
        this.saveAddress = saveAddress == null ? null : saveAddress.trim();
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getIsMessage() {
        return isMessage;
    }

    public void setIsMessage(Integer isMessage) {
        this.isMessage = isMessage;
    }

    public Integer getIsSms() {
        return isSms;
    }

    public void setIsSms(Integer isSms) {
        this.isSms = isSms;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
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
}