package com.zjzmjr.core.model.logo;

import java.io.Serializable;
import java.util.Date;

/**
 * 图标管理 t_logo_manage model类
 * 
 * @author lenovo
 * @version $Id: LogoManage.java, v 0.1 2016-9-18 下午4:37:36 lenovo Exp $
 */
public class LogoManage implements Serializable{

    /**  */
    private static final long serialVersionUID = 6207131614126984463L;

    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 图标类型编号
     */
    private Integer logoTypeNo;
    
    /**
     * 图标编号
     */
    private Integer logoNo;
    
    /**
     * 图标说明
     */
    private String logoComment;
    
    /**
     * 图标地址
     */
    private String logoAddress;
    
    /**
     * 图标链接地址
     */
    private String logoLinkAddress;
    
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


    @Override
    public String toString() {
        return "LogoManage [id=" + id + ", logoTypeNo=" + logoTypeNo + ", logoNo=" + logoNo + ", logoComment=" + logoComment + ", logoAddress=" + logoAddress + ", logoLinkAddress=" + logoLinkAddress + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }
    
}
