package com.zjzmjr.core.model.company;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.project.GardenProject;

/**
 * 工作汇报的详细信息
 * 
 * @author oms
 * @version $Id: WorkReportInfo.java, v 0.1 2017-9-13 下午3:16:59 oms Exp $
 */
public class WorkReportInfo extends WorkReport {

    /**  */
    private static final long serialVersionUID = -4851680955964649039L;

    /**
     * 项目
     */
    private GardenProject project;

    /**
     * 基础数据
     */
    private BasicData basic;

    /**
     * 用户信息
     */
    private Admin user;

    public GardenProject getProject() {
        return project;
    }

    public void setProject(GardenProject project) {
        this.project = project;
    }

    public BasicData getBasic() {
        return basic;
    }

    public void setBasic(BasicData basic) {
        this.basic = basic;
    }

    public Admin getUser() {
        return user;
    }

    public void setUser(Admin user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "WorkReportInfo [project=" + project + ", basic=" + basic + ", user=" + user + ", toString()=" + super.toString() + "]";
    }

}
