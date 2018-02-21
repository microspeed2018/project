package com.zjzmjr.core.model.logo;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;


/**
 * 图标管理 query
 * 
 * @author lenovo
 * @version $Id: LogoManageQuery.java, v 0.1 2016-9-21 上午9:21:34 lenovo Exp $
 */
public class LogoManageQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -663924304087305162L;

    private Integer id;
    
    private Integer logoTypeNo;
    
    private Integer logoNo;
    
    private String logoComment;
    
    private String logoAddress;
    
    private String logoLinkAddress;
    
    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;
    
    private Date lastTime;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getLogoTypeNo() {
        return logoTypeNo;
    }

    
    public void setLogoTypeNo(Integer logoTypeNo) {
        this.logoTypeNo = logoTypeNo;
    }

    
    public Integer getLogoNo() {
        return logoNo;
    }

    
    public void setLogoNo(Integer logoNo) {
        this.logoNo = logoNo;
    }

    
    public String getLogoComment() {
        return logoComment;
    }

    
    public void setLogoComment(String logoComment) {
        this.logoComment = logoComment;
    }

    
    public String getLogoAddress() {
        return logoAddress;
    }

    
    public void setLogoAddress(String logoAddress) {
        this.logoAddress = logoAddress;
    }

    
    public String getLogoLinkAddress() {
        return logoLinkAddress;
    }

    
    public void setLogoLinkAddress(String logoLinkAddress) {
        this.logoLinkAddress = logoLinkAddress;
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

    
    public Date getLastTime() {
        return lastTime;
    }


    
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }


    @Override
    public String toString() {
        return "LogoManageQuery [id=" + id + ", logoTypeNo=" + logoTypeNo + ", logoNo=" + logoNo + ", logoComment=" + logoComment + ", logoAddress=" + logoAddress + ", logoLinkAddress=" + logoLinkAddress + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", lastTime=" + lastTime + "]";
    }

    
    
    

}
