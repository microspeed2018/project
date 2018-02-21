package com.zjzmjr.core.model.company;

import java.io.Serializable;
import java.util.Date;

/**
 * 职位菜单信息表
 * 
 * @author oms
 * @version $Id: JobMenu.java, v 0.1 2017-8-29 下午4:32:16 oms Exp $
 */
public class JobMenu implements Serializable {

    /**  */
    private static final long serialVersionUID = -5836734982666863793L;

    private Integer id;

    private Integer companyId;

    private Integer jobId;

    private Integer menuId;

    private Date createTime;

    private Integer createUserId;

    private Integer version;

    public JobMenu(Integer companyId, Integer jobId, Integer menuId, Date createTime, Integer createUserId) {
        super();
        this.companyId = companyId;
        this.jobId = jobId;
        this.menuId = menuId;
        this.createTime = createTime;
        this.createUserId = createUserId;
    }

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

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "JobMenu [id=" + id + ", companyId=" + companyId + ", jobId=" + jobId + ", menuId=" + menuId + ", createTime=" + createTime + ", createUserId=" + createUserId + ", version=" + version + "]";
    }

}
