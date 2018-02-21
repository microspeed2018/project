package com.zjzmjr.admin.web.app.form;

import org.springframework.web.multipart.MultipartFile;

import com.zjzmjr.core.common.biz.BaseForm;

public class AppReleaseForm extends BaseForm {

    private static final long serialVersionUID = -2263315389839193684L;

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
    private MultipartFile downloadUrl;

    /**
     * 发布时间
     */
    private String createTime;

    private String lastTime;

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
        this.appVersion = appVersion;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public MultipartFile getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(MultipartFile downloadUrl) {
        this.downloadUrl = downloadUrl;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "AppReleaseForm [id=" + id + ", product=" + product + ", device=" + device + ", appVersion=" + appVersion + ", buildVersion=" + buildVersion + ", downloadUrl=" + downloadUrl + ", createTime=" + createTime + ", lastTime=" + lastTime + ", version=" + version + "]";
    }

    @Override
    public String resolveFiled(String field) {
        return null;
    }

}
