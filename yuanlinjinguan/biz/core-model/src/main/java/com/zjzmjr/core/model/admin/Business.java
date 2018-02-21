package com.zjzmjr.core.model.admin;

import java.io.Serializable;
import java.util.Date;

/**
 * 事物 model
 * 
 * @author Administrator
 * @version $Id: Business.java, v 0.1 2016-5-31 下午5:07:10 Administrator Exp $
 */
public class Business implements Serializable{
    private static final long serialVersionUID = 1871535522618367498L;
    
    private Integer id;

    /**
     * userId 用户编号
     * userName 用户名
     * businessType 事物类型
     * result 处理结果
     * comment 结果备注
     * accessIp 访问IP
     * extend1 扩展字段1
     * extend2 扩展字段2
     * extend3 扩展字段3
     * time 时间格式化字段
     */
    private Integer userId;
    
    private String userName;

    private Integer businessType;
    
    private Integer result;
    
    private String comment;

    private String accessIp;

    private String extend1;

    private String extend2;

    private String extend3;

    private Date createTime;
    
    private String time;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
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
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Business [id=" + id + ", userId=" + userId + ", userName=" + userName + ", businessType=" + businessType + ", result=" + result + ", comment=" + comment + ", accessIp=" + accessIp + ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3=" + extend3 + ", createTime=" + createTime + ", time=" + time + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}