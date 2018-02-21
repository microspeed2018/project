package com.zjzmjr.core.model.audit;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.project.GardenProject;


/**
 * 院办审核关联
 * 
 * @author lenovo
 * @version $Id: GardenProjectOfficeAudit.java, v 0.1 2017-8-31 下午7:25:16 lenovo Exp $
 */
public class GardenProjectOfficeAudit extends OfficeAudit{
    
    /**  */
    private static final long serialVersionUID = 5094420663605574774L;
    
    /**
     * 项目表
     */
    private GardenProject gardenProject;
    
    /**
     * 负责人姓名
     */
    private String projectLeaderName;
    
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
        return "GardenProjectOfficeAudit [gardenProject=" + gardenProject + ", projectLeaderName=" + projectLeaderName + ", admin=" + admin + ", basicData=" + basicData + ", paymentModeName=" + paymentModeName + "]";
    }

    
}
