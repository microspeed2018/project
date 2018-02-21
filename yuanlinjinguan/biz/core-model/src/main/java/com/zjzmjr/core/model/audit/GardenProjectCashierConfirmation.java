package com.zjzmjr.core.model.audit;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.project.GardenProject;

/**
 * 出纳确认关联model
 * 
 * @author lenovo
 * @version $Id: GardenProjectCarshierConfirmation.java, v 0.1 2017-9-1 下午4:17:32 lenovo Exp $
 */
public class GardenProjectCashierConfirmation extends CashierConfirmation{

    /**  */
    private static final long serialVersionUID = 2646606988747486901L;

    /**
     * 付款方式名称
     */
    private String paymentModeName;
    
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

    
    public String getProjectLeaderName() {
        return projectLeaderName;
    }


    
    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
    }


    @Override
    public String toString() {
        return "GardenProjectCashierConfirmation [paymentModeName=" + paymentModeName + ", gardenProject=" + gardenProject + ", admin=" + admin + ", basicData=" + basicData + ", projectLeaderName=" + projectLeaderName + "]";
    }


}
