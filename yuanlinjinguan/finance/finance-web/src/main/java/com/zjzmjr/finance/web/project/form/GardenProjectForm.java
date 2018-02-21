package com.zjzmjr.finance.web.project.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: GardenProjectForm.java, v 0.1 2017-8-14 下午1:45:45 oms Exp $
 */
public class GardenProjectForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = -1232674001673399779L;

    private Integer id;

    private String projectNo;

    private String name;

    /**
     * step值
     */
    private Integer step;

    /**
     * 最小的step值
     */
    private Integer minStep;

    private Integer status;

    /**
     * 是否是工作汇报
     */
    private Integer workType;
    
    /**
     * 申请退保证金金额
     */
    private String nobackBail;
    
    /**
     * 审核类型
     */
    private Integer bailType;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getMinStep() {
        return minStep;
    }

    public void setMinStep(Integer minStep) {
        this.minStep = minStep;
    }
    
    public String getNobackBail() {
        return nobackBail;
    }

    
    public void setNobackBail(String nobackBail) {
        this.nobackBail = nobackBail;
    }
    
    public Integer getBailType() {
        return bailType;
    }

    
    public void setBailType(Integer bailType) {
        this.bailType = bailType;
    }

    @Override
    public String toString() {
        return "GardenProjectForm [id=" + id + ", projectNo=" + projectNo + ", name=" + name + ", step=" + step + ", minStep=" + minStep + ", status=" + status + ", workType=" + workType + ", nobackBail=" + nobackBail + ", bailType=" + bailType + "]";
    }

}
