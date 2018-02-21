package com.zjzmjr.core.model.company;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 职位菜单查询条件
 * 
 * @author oms
 * @version $Id: RoleMenuQuery.java, v 0.1 2017-2-14 上午10:30:53 oms Exp $
 */
public class JobMenuQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 7919589523950812022L;

    private Integer id;

    private Integer jobId;

    private Integer menuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "JobMenuQuery [id=" + id + ", jobId=" + jobId + ", menuId=" + menuId + "]";
    }

}
