package com.zjzmjr.finance.web.fileUpload.form;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 附件上传FORM
 * 
 * @author oms
 * @version $Id: ReceiveFileForm.java, v 0.1 2017-8-21 上午11:21:42 oms Exp $
 */
public class ReceiveFileForm extends AbstractForm {

    /**  */
    private static final long serialVersionUID = 3953546511059677709L;

    private Integer id;

    private String fileName;

    private MultipartFile[] fileAddress;

    private String fileUrl;

    private Integer type;

    private Integer status;

    /**
     * 文件制作人编号
     */
    private Integer producerId;

    /**
     * 文件类型编号
     */
    private String fileId;

    /**
     * 项目编号
     */
    private Integer projectId;

    /** 上传图片业务类型 */
    private String business;
    
    /**
     * 基础数据表类别编号
     */
    private Integer categoryId;
    
    /**
     * 基础数据表区分编号
     */
    private Integer attributeId;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

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
        this.fileName = fileName;
    }

    public MultipartFile[] getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(MultipartFile[] fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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

    @Override
    public String toString() {
        return "ReceiveFileForm [id=" + id + ", fileName=" + fileName + ", fileAddress=" + Arrays.toString(fileAddress) + ", fileUrl=" + fileUrl + ", type=" + type + ", status=" + status + ", producerId=" + producerId + ", fileId=" + fileId + ", projectId=" + projectId + ", business=" + business + ", categoryId=" + categoryId + ", attributeId=" + attributeId + "]";
    }

}
