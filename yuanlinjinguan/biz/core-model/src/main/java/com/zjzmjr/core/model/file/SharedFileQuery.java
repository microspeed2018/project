package com.zjzmjr.core.model.file;

import com.zjzmjr.core.base.page.BasePageQuery;

public class SharedFileQuery extends BasePageQuery {

    private static final long serialVersionUID = -5284427743423677251L;

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

    /**
     * 上传人姓名
     */
    private String name;

    /**
     * 录入开始时间
     */
    private String startDate;

    /**
     * 录入结束时间
     */
    private String endDate;

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
        this.fileName = fileName;
    }

    public String getFileExplain() {
        return fileExplain;
    }

    public void setFileExplain(String fileExplain) {
        this.fileExplain = fileExplain;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "SharedFileQuery [id=" + id + ", companyId=" + companyId + ", sharedStaff=" + sharedStaff + ", fileName=" + fileName + ", fileExplain=" + fileExplain + ", saveName=" + saveName + ", saveAddress=" + saveAddress + ", sourceType=" + sourceType + ", isMessage=" + isMessage + ", isSms=" + isSms + ", isTop=" + isTop + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

}
