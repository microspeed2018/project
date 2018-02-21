package com.zjzmjr.admin.web.transaction.form;

import java.util.Date;

import com.zjzmjr.core.common.biz.BaseForm;


public class BusinessQueryForm extends BaseForm{
    
    private static final long serialVersionUID = -6607382654133948147L;
    
    private Integer id;

    private Integer userId;
    
    private String userName;

    private Integer businessType;
    
    private String businessName;

    private Integer result;
    
    private String resultName;

    private String comment;

    private String extend1;

    private String extend2;

    private String extend3;

    private Date createTime;
    
    private String time;

    
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

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName = userName;
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

    
    public String getResultName() {
        return resultName;
    }

    
    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    
    public String getComment() {
        return comment;
    }

    
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    public String getExtend1() {
        return extend1;
    }

    
    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    
    public String getExtend2() {
        return extend2;
    }

    
    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    
    public String getExtend3() {
        return extend3;
    }

    
    public void setExtend3(String extend3) {
        this.extend3 = extend3;
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


    @Override
    public String toString() {
        return "BusinessQueryForm [id=" + id + ", userId=" + userId + ", userName=" + userName + ", businessType=" + businessType + ", businessName=" + businessName + ", result=" + result + ", resultName=" + resultName + ", comment=" + comment + ", extend1=" + extend1 + ", extend2=" + extend2 + ", extend3=" + extend3 + ", createTime=" + createTime + ", time=" + time + "]";
    }


    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

}
