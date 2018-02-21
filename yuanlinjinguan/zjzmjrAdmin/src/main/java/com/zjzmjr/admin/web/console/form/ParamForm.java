/**
 * zjzmjr.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.form;

import java.util.Date;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 
 * 
 * @author qibin
 * @version $Id: ParamForm.java, v 0.1 2015-8-4 上午11:14:21 qibin Exp $
 */
public class ParamForm extends AbstractForm {

    private static final long serialVersionUID = -6373886144796238673L;


    /**
     * 参数ID
     */
    private Integer id;

    /**
     * 变量名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 值
     */
    private String value;

    private Date timeStart;

    private Date timeEnd;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String resolveFiled(String field) {
        if (id.equals(field)) {
            return "参数ID";
        } else if (name.equals(field)) {
            return "参数名称";
        } else if (value.equals(field)) {
            return "参数值";
        } else if (remark.equals(field)) {
            return "备注";
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((timeEnd == null) ? 0 : timeEnd.hashCode());
        result = prime * result + ((timeStart == null) ? 0 : timeStart.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ParamForm other = (ParamForm) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (timeEnd == null) {
            if (other.timeEnd != null)
                return false;
        } else if (!timeEnd.equals(other.timeEnd))
            return false;
        if (timeStart == null) {
            if (other.timeStart != null)
                return false;
        } else if (!timeStart.equals(other.timeStart))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ParamForm [id=" + id + ", name=" + name + ", value=" + value + ", timeStart=" + timeStart
                + ", timeEnd=" + timeEnd + "]";
    }

}
