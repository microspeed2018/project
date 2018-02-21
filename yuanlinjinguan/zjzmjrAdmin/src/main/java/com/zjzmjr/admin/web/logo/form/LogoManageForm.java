package com.zjzmjr.admin.web.logo.form;

import org.springframework.web.multipart.MultipartFile;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 图标管理form
 * 
 * @author lenovo
 * @version $Id: LogoManageForm.java, v 0.1 2016-9-20 下午5:06:24 lenovo Exp $
 */
public class LogoManageForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = -1531801530157130465L;

    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 图标类型编号
     * 1.个人设备区
     * 2.业务代理区
     * 3.业务功能区
     * 4.业务查询区
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
    private MultipartFile logoAddress;
    
    /**
     * 图标链接地址
     */
    private String logoLinkAddress;
    
    private String createTime;

    private Integer createUserId;

    private Integer version;
    
    private String lastTime;
    
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

    
    
    public MultipartFile getLogoAddress() {
        return logoAddress;
    }


    
    public void setLogoAddress(MultipartFile logoAddress) {
        this.logoAddress = logoAddress;
    }


    public String getLogoLinkAddress() {
        return logoLinkAddress;
    }

    
    public void setLogoLinkAddress(String logoLinkAddress) {
        this.logoLinkAddress = logoLinkAddress;
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


    
    public String getCreateTime() {
        return createTime;
    }


    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    
    public String getLastTime() {
        return lastTime;
    }


    
    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }


    @Override
    public String resolveFiled(String arg0) {
        return null;
    }


    @Override
    public String toString() {
        return "LogoManageForm [id=" + id + ", logoTypeNo=" + logoTypeNo + ", logoNo=" + logoNo + ", logoComment=" + logoComment + ", logoAddress=" + logoAddress + ", logoLinkAddress=" + logoLinkAddress + ", createTime=" + createTime + ", createUserId=" + createUserId + ", version=" + version + ", lastTime=" + lastTime + "]";
    }


    


}
