package com.zjzmjr.core.model.holiday;

import java.io.Serializable;
import java.util.Date;

/**
 * 节假日表实体类
 * 
 * @author chenning
 * @version $Id: Holiday.java, v 0.1 2016-10-14 下午12:10:53 chenning Exp $
 */
public class Holiday implements Serializable {

    private static final long serialVersionUID = -7779422707652884232L;

    // 主键ID
    private Integer id;

    // 节假日时间
    private String holidayTime;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    /** 是否节假日 0:调休工作日 1:节假日 */
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayTime() {
        return holidayTime;
    }

    public void setHolidayTime(String holidayTime) {
        this.holidayTime = holidayTime == null ? null : holidayTime.trim();
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

    public Integer getFlag() {
        return flag;
    }
    /**前台展示属性*/
    public String getTflag() {
        if(this.flag==1){
            return "节假日";
        }else if(this.flag==0){
            return "调休工作日";
        }else{
            return "";
        }
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Holiday [id=" + id + ", holidayTime=" + holidayTime + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", flag=" + flag + "]";
    }

}