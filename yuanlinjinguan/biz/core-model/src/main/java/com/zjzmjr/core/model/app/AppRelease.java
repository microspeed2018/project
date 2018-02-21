package com.zjzmjr.core.model.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_app_releaseApp发布管理表
 * 
 * @author Administrator
 * @version $Id: AppRelease.java, v 0.1 2016-11-17 上午10:25:34 Administrator Exp $
 */
public class AppRelease implements Serializable {

    private static final long serialVersionUID = -6143376879157448003L;

    /**
     * 主键编号
     */
    private Integer id;

    /**
     * 版本
     */
    private Integer product;

    /**
     * 版本类型
     */
    private Integer device;

    /**
     * 版本号
     */
    private String appVersion;

    /**
     * 构建版本号
     */
    private String buildVersion;

    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 描述
     */
    private String appComment;

    /**
     * 是否强制更新
     */
    private Integer forceUpdate;

    /**
     * 文件大小
     */
    private BigDecimal pkgSize;

    /**
     * 发布时间
     */
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

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion == null ? null : appVersion.trim();
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion == null ? null : buildVersion.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getAppComment() {
        return appComment;
    }

    public void setAppComment(String appComment) {
        this.appComment = appComment == null ? null : appComment.trim();
    }

    public Integer getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Integer forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public BigDecimal getPkgSize() {
        return pkgSize;
    }

    public void setPkgSize(BigDecimal pkgSize) {
        this.pkgSize = pkgSize;
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
        return "AppRelease [id=" + id + ", product=" + product + ", device=" + device + ", appVersion=" + appVersion + ", buildVersion=" + buildVersion + ", downloadUrl=" + downloadUrl + ", appComment=" + appComment + ", forceUpdate=" + forceUpdate + ", pkgSize=" + pkgSize + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }
}