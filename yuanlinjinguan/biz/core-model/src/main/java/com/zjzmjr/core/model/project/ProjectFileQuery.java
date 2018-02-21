package com.zjzmjr.core.model.project;

import java.util.List;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 资料上传查询条件
 * 
 * @author oms
 * @version $Id: ProjectFileQuery.java, v 0.1 2017-9-1 下午8:30:55 oms Exp $
 */
public class ProjectFileQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = -4295039361119047853L;

    private Integer id;

    private Integer companyId;
    
    private Integer basicId;

    private Integer projectId;
    
    private Integer adminId;

    /**
     * 文件编号
     */
    private Integer fileId;

    /**
     * 一系列文件编号
     */
    private List<String> fileIdLst;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 上传的文件名
     */
    private String fileName;

    /**
     * 文件上传开始日期
     */
    private String uploadDateStart;

    /**
     * 文件上传结束日期
     */
    private String uploadDateEnd;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 对文件的操作权限， 1 ： 操作权限 2： 查看权限
     */
    private Integer handlerJob;
    
    private Integer categoryId;
    
    private Integer attributeId;

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
    
    public Integer getBasicId() {
        return basicId;
    }
    
    public void setBasicId(Integer basicId) {
        this.basicId = basicId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public List<String> getFileIdLst() {
        return fileIdLst;
    }

    public void setFileIdLst(List<String> fileIdLst) {
        this.fileIdLst = fileIdLst;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadDateStart() {
        return uploadDateStart;
    }

    public void setUploadDateStart(String uploadDateStart) {
        this.uploadDateStart = uploadDateStart;
    }

    public String getUploadDateEnd() {
        return uploadDateEnd;
    }

    public void setUploadDateEnd(String uploadDateEnd) {
        this.uploadDateEnd = uploadDateEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 对文件的操作权限， 1 ： 操作权限 2： 查看权限
     * 
     * @return
     */
    public Integer getHandlerJob() {
        return handlerJob;
    }

    /**
     * 对文件的操作权限， 1 ： 操作权限 2： 查看权限
     * 
     * @param handlerJob
     */
    public void setHandlerJob(Integer handlerJob) {
        this.handlerJob = handlerJob;
    }
    
    public Integer getCategoryId() {
        return categoryId;
    }

    
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    
    public Integer getAttributeId() {
        return attributeId;
    }

    
    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }
    
    public Integer getAdminId() {
        return adminId;
    }

    
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "ProjectFileQuery [id=" + id + ", companyId=" + companyId + ", basicId=" + basicId + ", projectId=" + projectId + ", adminId=" + adminId + ", fileId=" + fileId + ", fileIdLst=" + fileIdLst + ", userId=" + userId + ", fileName=" + fileName + ", uploadDateStart=" + uploadDateStart + ", uploadDateEnd=" + uploadDateEnd + ", status=" + status + ", handlerJob=" + handlerJob + ", categoryId=" + categoryId + ", attributeId=" + attributeId + "]";
    }

}
