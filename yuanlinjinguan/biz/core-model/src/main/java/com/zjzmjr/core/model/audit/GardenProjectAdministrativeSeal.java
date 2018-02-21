package com.zjzmjr.core.model.audit;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.project.GardenProject;

/**
 * 行政盖章关联model
 * 
 * @author lenovo
 * @version $Id: GardenProjectAdministrativeSeal.java, v 0.1 2017-9-2 上午11:25:52 lenovo Exp $
 */
public class GardenProjectAdministrativeSeal extends AdministrativeSeal{

    /**  */
    private static final long serialVersionUID = 6793241541627185833L;

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


    @Override
    public String toString() {
        return "GardenProjectAdministrativeSeal [gardenProject=" + gardenProject + ", admin=" + admin + ", basicData=" + basicData + "]";
    }
        
}
