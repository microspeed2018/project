package com.zjzmjr.core.model.project;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 合同付款的检索条件
 * 
 * @author oms
 * @version $Id: ContractPaymentQuery.java, v 0.1 2017-9-18 下午5:31:31 oms Exp $
 */
public class ContractPaymentQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = -1189100919968329561L;

    private Integer companyId;

    private Integer projectId;

    private Integer paymentMode;

    private Integer projectLeader;

    private String projectName;

    /**
     * 财务审核表中的审核类型
     */
    private Integer verifyType;
    
    private Integer status;

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

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Integer projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(Integer verifyType) {
        this.verifyType = verifyType;
    }
    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContractPaymentQuery [companyId=" + companyId + ", projectId=" + projectId + ", paymentMode=" + paymentMode + ", projectLeader=" + projectLeader + ", projectName=" + projectName + ", verifyType=" + verifyType + ", status=" + status + "]";
    }

}
