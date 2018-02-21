package com.zjzmjr.core.model.audit;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.project.GardenProject;

/**
 * 法务审核关联
 * 
 * @author lenovo
 * @version $Id: GardenProjectLawAudit.java, v 0.1 2017-9-4 下午5:04:56 lenovo Exp $
 */
public class GardenProjectLawAudit extends LawAudit{

    /**  */
    private static final long serialVersionUID = 7863047206657271491L;

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
        return "GardenProjectLawAudit [gardenProject=" + gardenProject + ", admin=" + admin + ", basicData=" + basicData + "]";
    }

}
