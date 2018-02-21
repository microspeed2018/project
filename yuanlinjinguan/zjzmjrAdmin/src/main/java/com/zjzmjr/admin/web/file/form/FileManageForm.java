package com.zjzmjr.admin.web.file.form;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 附件上传FORM
 * 
 * @author Administrator
 * @version $Id: FileManageForm.java, v 0.1 2016-11-21 上午10:35:30 Administrator
 *          Exp $
 */
public class FileManageForm extends BaseForm {

    private static final long serialVersionUID = -6111287125670364167L;

    private Integer id;

    private String fileName;

    private MultipartFile[] fileAddress;

    /**
     * 大文件过来的访问URL
     */
    private String fileAccessUrl;

    /**
     * 上传人姓名
     */
    private Integer name;

    /**
     * 数据来源
     */
    private Integer sourceType;

    /**
     * 文件上传开始日期
     */
    private String uploadDateStart;

    /**
     * 文件上传结束日期
     */
    private String uploadDateEnd;

    /**
     * 文件类型id
     */
    private Integer fileId;

    /**
     * 一系列文件编号
     */
    private String fileIds;

    /**
     * 项目编号
     */
    private Integer projectId;
    
    /**
     * 员工编号
     */
    private Integer adminId;
    
    /**
     * 资料类别id
     */
    private Integer basicId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public MultipartFile[] getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(MultipartFile[] fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getFileAccessUrl() {
        return fileAccessUrl;
    }

    public void setFileAccessUrl(String fileAccessUrl) {
        this.fileAccessUrl = fileAccessUrl;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
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

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public Integer getAdminId() {
        return adminId;
    }

    
    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
    
    public Integer getBasicId() {
        return basicId;
    }

    
    public void setBasicId(Integer basicId) {
        this.basicId = basicId;
    }

    @Override
    public String toString() {
        return "FileManageForm [id=" + id + ", fileName=" + fileName + ", fileAddress=" + Arrays.toString(fileAddress) + ", fileAccessUrl=" + fileAccessUrl + ", name=" + name + ", sourceType=" + sourceType + ", uploadDateStart=" + uploadDateStart + ", uploadDateEnd=" + uploadDateEnd + ", fileId=" + fileId + ", fileIds=" + fileIds + ", projectId=" + projectId + ", adminId=" + adminId + ", basicId=" + basicId + "]";
    }

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }
}