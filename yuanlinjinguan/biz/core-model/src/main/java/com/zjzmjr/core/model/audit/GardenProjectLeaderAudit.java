package com.zjzmjr.core.model.audit;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.project.GardenProject;

/**
 * 技术审核关联
 * 
 * @author lenovo
 * @version $Id: GardenProjectLeaderAudit.java, v 0.1 2017-9-4 下午5:05:13 lenovo Exp $
 */
public class GardenProjectLeaderAudit extends LeaderAudit{
    
    /**  */
    private static final long serialVersionUID = 4843931474837768180L;

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
     * 付款方式名称
     */
    private String paymentModeName;
    
    /**
     * 负责人名称
     */
    private String projectLeaderName;

    
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

    
    public String getPaymentModeName() {
        return paymentModeName;
    }


    
    public void setPaymentModeName(String paymentModeName) {
        this.paymentModeName = paymentModeName;
    }

    
    public String getProjectLeaderName() {
        return projectLeaderName;
    }


    
    public void setProjectLeaderName(String projectLeaderName) {
        this.projectLeaderName = projectLeaderName;
    }


    @Override
    public String toString() {
        return "GardenProjectLeaderAudit [gardenProject=" + gardenProject + ", admin=" + admin + ", basicData=" + basicData + ", paymentModeName=" + paymentModeName + ", projectLeaderName=" + projectLeaderName + "]";
    }
    

}
