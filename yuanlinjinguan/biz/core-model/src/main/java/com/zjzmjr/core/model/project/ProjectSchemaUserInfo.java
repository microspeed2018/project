package com.zjzmjr.core.model.project;

import com.zjzmjr.core.model.admin.Admin;

/**
 * 项目方案及方案提案人信息
 * 
 * @author oms
 * @version $Id: ProjectSchemaUserInfo.java, v 0.1 2017-8-29 下午5:12:28 oms Exp $
 */
public class ProjectSchemaUserInfo extends GardenProjectSchema {

    /**  */
    private static final long serialVersionUID = 984218241706544374L;

    private Admin userInfo;

    public Admin getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Admin userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "ProjectSchemaUserInfo [userInfo=" + userInfo + ", toString()=" + super.toString() + "]";
    }

}
