package com.zjzmjr.core.model.admin;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 管理员事物表实体类
 * 
 * @author Administrator
 * @version $Id: AdminBusiness.java, v 0.1 2016-10-9 下午2:14:29 Administrator Exp
 *          $
 */
public class AdminBusiness implements Serializable {

    private static final long serialVersionUID = -2937232442922302483L;

    private Integer id;

    /**
     * 公司编号
     */
    private Integer companyId;

    /**
     * 项目编号
     */
    private Integer projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 管理员标号
     */
    private Integer adminId;

    /**
     * 管理员名称
     */
    private String adminName;

    /**
     * 事物类型
     */
    private Integer businessType;

    /**
     * 事务类型名称
     */
    private String businessName;

    /**
     * 处理结果
     */
    private Integer result;

    /**
     * 结果备注
     */
    private String comment;

    /**
     * 访问的设备类型
     */
    private Integer deviceType;

    /**
     * 访问的设备信息
     */
    private String deviceInfo;

    /**
     * 访问IP
     */
    private String accessIp;

    /**
     * 拓展字段1
     */
    private String extend1;

    /**
     * 拓展字段2
     */
    private String extend2;

    /**
     * 拓展字段3
     */
    private String extend3;

    /**
     * 创建时间格式化字段
     */
    private String time;

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp == null ? null : accessIp.trim();
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1 == null ? null : extend1.trim();
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2 == null ? null : extend2.trim();
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3 == null ? null : extend3.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        return "AdminBusiness [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", projectName=" + projectName + ", adminId=" + adminId + ", adminName=" + adminName + ", businessType=" + businessType + ", businessName=" + businessName + ", result=" + result + ", comment=" + comment + ", deviceType=" + deviceType + ", deviceInfo=" + deviceInfo + ", accessIp=" + accessIp + ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3=" + extend3 + ", time=" + time
                + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}