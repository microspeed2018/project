package com.zjzmjr.finance.web.project.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 项目分包form
 * 
 * @author oms
 * @version $Id: SubpackageForm.java, v 0.1 2017-9-3 下午6:34:18 oms Exp $
 */
public class SubpackageForm extends AbstractForm {

    /**  */
    private static final long serialVersionUID = 9022208128929533490L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    // 分包负责人
    private Integer subpackageLeader;

    // 分包金额
    private String subpackageCapital;

    // 分包内容
    private String subpackageContent;

    // 支付方式
    private String paymentMemo;

    // 分包备注
    private String subpackageMemo;

    private Integer status;
    
    /**
     * 项目步骤
     */
    private Integer step;
    
    /**
     * 分包付款方式拼接字符串
     */
    private String payModes;
    
    /**
     * 分包付款金额拼接字符串
     */
    private String payAmounts;

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

    public Integer getSubpackageLeader() {
        return subpackageLeader;
    }

    public void setSubpackageLeader(Integer subpackageLeader) {
        this.subpackageLeader = subpackageLeader;
    }

    public String getSubpackageCapital() {
        return subpackageCapital;
    }

    public void setSubpackageCapital(String subpackageCapital) {
        this.subpackageCapital = subpackageCapital;
    }

    public String getSubpackageContent() {
        return subpackageContent;
    }

    public void setSubpackageContent(String subpackageContent) {
        this.subpackageContent = subpackageContent;
    }

    public String getPaymentMemo() {
        return paymentMemo;
    }

    public void setPaymentMemo(String paymentMemo) {
        this.paymentMemo = paymentMemo;
    }

    public String getSubpackageMemo() {
        return subpackageMemo;
    }

    public void setSubpackageMemo(String subpackageMemo) {
        this.subpackageMemo = subpackageMemo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public String getPayModes() {
        return payModes;
    }

    
    public void setPayModes(String payModes) {
        this.payModes = payModes;
    }

    
    public String getPayAmounts() {
        return payAmounts;
    }

    
    public void setPayAmounts(String payAmounts) {
        this.payAmounts = payAmounts;
    }
    
    public Integer getStep() {
        return step;
    }

    
    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "SubpackageForm [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", subpackageLeader=" + subpackageLeader + ", subpackageCapital=" + subpackageCapital + ", subpackageContent=" + subpackageContent + ", paymentMemo=" + paymentMemo + ", subpackageMemo=" + subpackageMemo + ", status=" + status + ", step=" + step + ", payModes=" + payModes + ", payAmounts=" + payAmounts + "]";
    }

}
