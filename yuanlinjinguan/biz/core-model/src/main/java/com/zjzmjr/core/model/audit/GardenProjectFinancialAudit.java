package com.zjzmjr.core.model.audit;

import java.math.BigDecimal;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.project.GardenProject;


/**
 * 财务审核关联model
 * 
 * @author lenovo
 * @version $Id: GardenProjectFinancialAudit.java, v 0.1 2017-9-1 下午3:34:50 lenovo Exp $
 */
public class GardenProjectFinancialAudit extends FinancialAudit{

    /**  */
    private static final long serialVersionUID = -1950572423148959665L;

    /**
     * 付款方式名称
     */
    private String paymentModeName;
    
    /**
     * 未开票金额
     */
    private BigDecimal notBillAmount;
    
    /**
     * 项目表
     */
    private GardenProject gardenProject;
    
    /**
     * 管理员表
     */
    private Admin admin;
    
    /**
     * 基础数据
     */
    private BasicData basicData;

    /**
     * 负责人名称
     */
    private String projectLeaderName;
    
    public String getPaymentModeName() {
        return paymentModeName;
    }

    
    public void setPaymentModeName(String paymentModeName) {
        this.paymentModeName = paymentModeName;
    }

    
    public GardenProject getGardenProject() {
        return gardenProject;
    }

    
    public void setGardenProject(GardenProject gardenProject) {
        this.gardenProject = gardenProject;
    }

    
    public Admin getAdmin() {
        return admin;
    }

    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    
    public BasicData getBasicData() {
        return basicData;
    }

    
    public void setBasicData(BasicData basicData) {
        this.basicData = basicData;
    }

    
    public BigDecimal getNotBillAmount() {
        return notBillAmount;
    }


    
    public void setNotBillAmount(BigDecimal notBillAmount) {
        this.notBillAmount = notBillAmount;
    }


    
    public String getProjectLeaderName() {
        return projectLeaderName;
    }


    
    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
    }


    @Override
    public String toString() {
        return "GardenProjectFinancialAudit [paymentModeName=" + paymentModeName + ", notBillAmount=" + notBillAmount + ", gardenProject=" + gardenProject + ", admin=" + admin + ", basicData=" + basicData + ", projectLeaderName=" + projectLeaderName + "]";
    }


}
