package com.zjzmjr.core.model.company;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: WorkReportQuery.java, v 0.1 2017-8-10 上午10:36:30 oms Exp $
 */
public class WorkReportQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 9056729565382362625L;

    private Integer id;

    private Integer userId;

    /** 项目编号 */
    private Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "WorkReportQuery [id=" + id + ", userId=" + userId + ", projectId=" + projectId + "]";
    }

}
