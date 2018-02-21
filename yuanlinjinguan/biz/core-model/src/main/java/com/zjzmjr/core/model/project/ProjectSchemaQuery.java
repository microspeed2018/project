package com.zjzmjr.core.model.project;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 项目方案表查询条件
 * 
 * @author oms
 * @version $Id: ProjectSchemaQuery.java, v 0.1 2017-8-16 上午10:31:51 oms Exp $
 */
public class ProjectSchemaQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 8610548169010953930L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    /**
     * 方案提议人
     */
    private Integer schemeProposer;

    /**
     * 是否被采纳
     */
    private Integer accepted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSchemeProposer() {
        return schemeProposer;
    }

    public void setSchemeProposer(Integer schemeProposer) {
        this.schemeProposer = schemeProposer;
    }

    public Integer getAccepted() {
        return accepted;
    }

    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "ProjectSchemaQuery [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", schemeProposer=" + schemeProposer + ", accepted=" + accepted + "]";
    }

}
