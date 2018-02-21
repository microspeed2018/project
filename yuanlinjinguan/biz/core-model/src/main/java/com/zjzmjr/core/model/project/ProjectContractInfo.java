package com.zjzmjr.core.model.project;

import java.util.List;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.audit.CashierConfirmation;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.company.CompanyAssociated;

/**
 * 项目合同及发包单位信息
 * 
 * @author oms
 * @version $Id: ProjectContractInfo.java, v 0.1 2017-8-27 下午9:45:27 oms Exp $
 */
public class ProjectContractInfo extends ProjectContract {

    /**  */
    private static final long serialVersionUID = 3827754429682197320L;

    // /**
    // * 合同付款表
    // */
    // private List<ContractPayment> payment;

    /**
     * 项目表
     */
    private GardenProject project;

    /**
     * 关联公司或发包单位
     */
    private CompanyAssociated associate;

    /**
     * 项目所在城市
     */
    private Area area;

    /**
     * 技术负责人
     */
    private Admin leader;

    /**
     * 经营专员
     */
    private Admin manager;

    /**
     * 分包项目
     */
    private List<ContractSubpackageInfo> contractSubpackage;

    /**
     * 出纳确认
     */
    private List<CashierConfirmation> cashierConfirmation;
    
    /**
     * 财务审核
     */
    private List<FinancialAudit> financialAudit;

    /**
     * 合同付款
     */
    private List<ContractPayment> contractPayment;
    
    /**
     * 创建时间
     */
    private String addTime;
    
    /**
     * 状态文本
     */
    private String StatusText;

    // public List<ContractPayment> getPayment() {
    // return payment;
    // }
    //
    // public void setPayment(List<ContractPayment> payment) {
    // this.payment = payment;
    // }

    public GardenProject getProject() {
        return project;
    }

    public void setProject(GardenProject project) {
        this.project = project;
    }

    public CompanyAssociated getAssociate() {
        return associate;
    }

    public void setAssociate(CompanyAssociated associate) {
        this.associate = associate;
    }

    public Admin getLeader() {
        return leader;
    }

    public void setLeader(Admin leader) {
        this.leader = leader;
    }

    public Admin getManager() {
        return manager;
    }

    public void setManager(Admin manager) {
        this.manager = manager;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
    public List<ContractSubpackageInfo> getContractSubpackage() {
        return contractSubpackage;
    }

    
    public void setContractSubpackage(List<ContractSubpackageInfo> contractSubpackage) {
        this.contractSubpackage = contractSubpackage;
    }

    public List<CashierConfirmation> getCashierConfirmation() {
        return cashierConfirmation;
    }

    public void setCashierConfirmation(List<CashierConfirmation> cashierConfirmation) {
        this.cashierConfirmation = cashierConfirmation;
    }

    public List<ContractPayment> getContractPayment() {
        return contractPayment;
    }

    public void setContractPayment(List<ContractPayment> contractPayment) {
        this.contractPayment = contractPayment;
    }
    
    public List<FinancialAudit> getFinancialAudit() {
        return financialAudit;
    }

    
    public void setFinancialAudit(List<FinancialAudit> financialAudit) {
        this.financialAudit = financialAudit;
    }
    
    public String getAddTime() {
        return addTime;
    }

    
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    
    public String getStatusText() {
        return StatusText;
    }

    
    public void setStatusText(String statusText) {
        StatusText = statusText;
    }

    @Override
    public String toString() {
        return "ProjectContractInfo [project=" + project + ", associate=" + associate + ", area=" + area + ", leader=" + leader + ", manager=" + manager + ", contractSubpackage=" + contractSubpackage + ", cashierConfirmation=" + cashierConfirmation + ", financialAudit=" + financialAudit + ", contractPayment=" + contractPayment + ", addTime=" + addTime + ", StatusText=" + StatusText + "]";
    }

}
