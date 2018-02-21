package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.util.Date;

/**
 * 上传文件类型表
 * 
 * @author oms
 * @version $Id: ProjectFile.java, v 0.1 2017-8-21 下午1:51:59 oms Exp $
 */
public class ProjectFile implements Serializable {

    /**  */
    private static final long serialVersionUID = -5793683412347653891L;

    private Integer id;

    private Integer companyId;
    
    private Integer basicId;

    private String name;
    
    private String attributeName;

    /**
     * 能够编辑的职位权限
     */
    private String editJob;

    /**
     * 能够查看的职位权限
     */
    private String viewJob;

    private Integer status;

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
    
    public Integer getBasicId() {
        return basicId;
    }
    
    public void setBasicId(Integer basicId) {
        this.basicId = basicId;
    }
    
    public String getAttributeName() {
        return attributeName;
    }
    
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEditJob() {
        return editJob;
    }

    public void setEditJob(String editJob) {
        this.editJob = editJob;
    }

    public String getViewJob() {
        return viewJob;
    }

    public void setViewJob(String viewJob) {
        this.viewJob = viewJob;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "ProjectFile [id=" + id + ", companyId=" + companyId + ", basicId=" + basicId + ", name=" + name + ", attributeName=" + attributeName + ", editJob=" + editJob + ", viewJob=" + viewJob + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}