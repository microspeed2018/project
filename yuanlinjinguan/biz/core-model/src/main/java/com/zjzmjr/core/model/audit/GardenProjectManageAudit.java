package com.zjzmjr.core.model.audit;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.project.GardenProject;

/**
 * 业务审核关联
 * 
 * @author lenovo
 * @version $Id: GardenProjectManageAudit.java, v 0.1 2017-8-31 下午7:10:57 lenovo Exp $
 */
public class GardenProjectManageAudit extends ManageAudit{

    /**  */
    private static final long serialVersionUID = -5443846707003647063L;

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
        return "GardenProjectManageAudit [gardenProject=" + gardenProject + ", admin=" + admin + ", basicData=" + basicData + ", projectLeaderName=" + projectLeaderName + "]";
    }

}
