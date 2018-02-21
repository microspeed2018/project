package com.zjzmjr.core.model.app;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * App发布管理查询类 Query
 * 
 * @author chenning
 * @version $Id: AppReleaseQuery.java, v 0.1 2016-11-17 上午10:35:10 Administrator Exp $
 */
public class AppReleaseQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 2825829073671161883L;

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
     * 发布时间
     */
    private Date createTime;

    private Date lastTime;

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
        this.appVersion = appVersion;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "AppReleaseQuery [id=" + id + ", product=" + product + ", device=" + device + ", appVersion=" + appVersion + ", buildVersion=" + buildVersion + ", downloadUrl=" + downloadUrl + ", createTime=" + createTime + ", lastTime=" + lastTime + "]";
    }

}
