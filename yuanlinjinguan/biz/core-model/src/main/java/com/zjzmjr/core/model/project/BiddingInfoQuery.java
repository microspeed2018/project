package com.zjzmjr.core.model.project;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 开标信息表查询条件类
 * 
 * @author oms
 * @version $Id: BiddingInfoQuery.java, v 0.1 2017-8-23 下午4:00:40 oms Exp $
 */
public class BiddingInfoQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = -4930094023215491114L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    /**
     * 投标公司的主键编号
     */
    private Integer bidCompany;

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

    public Integer getBidCompany() {
        return bidCompany;
    }

    public void setBidCompany(Integer bidCompany) {
        this.bidCompany = bidCompany;
    }

    @Override
    public String toString() {
        return "BiddingInfoQuery [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", bidCompany=" + bidCompany + "]";
    }

}
