package com.zjzmjr.core.model.company;

import java.util.List;

import com.zjzmjr.core.base.page.Base;

/**
 * 职位菜单绑定
 * 
 * @author oms
 * @version $Id: RoleMenuBind.java, v 0.1 2017-2-16 下午2:34:43 oms Exp $
 */
public class JobMenuBind extends Base {

    /**  */
    private static final long serialVersionUID = -3104823873524924569L;

    private Integer companyId;

    private Integer jobId;

    private List<Integer> menuIds;

    private Integer createUserId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString() {
        return "JobMenuBind [companyId=" + companyId + ", jobId=" + jobId + ", menuIds=" + menuIds + ", createUserId=" + createUserId + "]";
    }

}
