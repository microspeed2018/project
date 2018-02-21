package com.zjzmjr.core.model.project;

import java.util.List;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.company.BasicData;

/**
 * 项目、付款合同及财务审核的详细信息
 * 
 * @author oms
 * @version $Id: ContractPaymentInfo.java, v 0.1 2017-9-18 下午5:12:28 oms Exp $
 */
public class ContractPaymentInfo extends ContractPayment {

    /**  */
    private static final long serialVersionUID = -7445544009489443666L;

    /**
     * 项目
     */
    private GardenProject project;

    /**
     * 技术负责人
     */
    private Admin leader;

    /**
     * 财务审核数据
     */
    private List<FinancialAudit> finance;

    /**
     * 基础数据
     */
    private BasicData basic;

    public GardenProject getProject() {
        return project;
    }

    public void setProject(GardenProject project) {
        this.project = project;
    }

    public Admin getLeader() {
        return leader;
    }

    public void setLeader(Admin leader) {
        this.leader = leader;
    }

    public List<FinancialAudit> getFinance() {
        return finance;
    }

    public void setFinance(List<FinancialAudit> finance) {
        this.finance = finance;
    }

    public BasicData getBasic() {
        return basic;
    }

    public void setBasic(BasicData basic) {
        this.basic = basic;
    }

    @Override
    public String toString() {
        return "ContractPaymentInfo [project=" + project + ", leader=" + leader + ", finance=" + finance + ", basic=" + basic + ", toString()=" + super.toString() + "]";
    }

}
