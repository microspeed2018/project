package com.zjzmjr.admin.web.file.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 贷款基础资料表Form
 * 
 * @author Administrator
 * @version $Id: LoanBasicFileForm.java, v 0.1 2017-4-12 下午3:54:25 Administrator
 *          Exp $
 */
public class BasicFileForm extends BaseForm {

    private static final long serialVersionUID = -2061843420182447817L;

    private Integer id;

    /**
     * 文件名
     */
    private String fileName;
    
    /**
     * 基础表id
     */
    private Integer basicId;

    /**
     * 状态
     */
    private Integer status;
    
    private Integer categoryId;
    
    private Integer attributeId;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getBasicId() {
        return basicId;
    }
    
    public void setBasicId(Integer basicId) {
        this.basicId = basicId;
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
        return "BasicFileForm [id=" + id + ", fileName=" + fileName + ", basicId=" + basicId + ", status=" + status + ", categoryId=" + categoryId + ", attributeId=" + attributeId + "]";
    }

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

}