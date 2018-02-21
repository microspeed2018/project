package com.zjzmjr.core.model.audit;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.project.GardenProject;


/**
 * 总工审核关联model
 * 
 * @author lenovo
 * @version $Id: GardenProjectChiefAudit.java, v 0.1 2017-9-1 上午10:10:32 lenovo Exp $
 */
public class GardenProjectChiefAudit extends ChiefAudit{

    /**  */
    private static final long serialVersionUID = 1620184716481760759L;

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
        return "GardenProjectChiefAudit [gardenProject=" + gardenProject + ", admin=" + admin + ", basicData=" + basicData + "]";
    }
    
    
}
