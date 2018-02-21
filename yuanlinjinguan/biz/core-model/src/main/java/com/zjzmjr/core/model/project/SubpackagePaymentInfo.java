package com.zjzmjr.core.model.project;

import java.util.List;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.company.BasicData;

/**
 * 分包付款表及分包和项目信息的总览
 * 
 * @author oms
 * @version $Id: SubpackagePaymentInfo.java, v 0.1 2017-9-28 上午10:42:11 oms Exp
 *          $
 */
public class SubpackagePaymentInfo extends SubpackagePayment {

    /**  */
    private static final long serialVersionUID = 5803233640486304418L;

    /**
     * 分包表信息
     */
    private ContractSubpackage subpackage;

    /**
     * 项目信息
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

    public ContractSubpackage getSubpackage() {
        return subpackage;
    }

    public void setSubpackage(ContractSubpackage subpackage) {
        this.subpackage = subpackage;
    }

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
        return "SubpackagePaymentInfo [subpackage=" + subpackage + ", project=" + project + ", leader=" + leader + ", finance=" + finance + ", basic=" + basic + ", toString()=" + super.toString() + "]";
    }

}
