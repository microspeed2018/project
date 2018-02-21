package com.zjzmjr.finance.web.company.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: WorkReportForm.java, v 0.1 2017-8-10 下午2:42:07 oms Exp $
 */
public class WorkReportForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = -2768724698083577380L;

    private Integer projectId;

    private Integer workTypeId;

    /**
     * 工作内容
     */
    private String workContent;

    /**
     * 工作照片
     */
    private String workProof;

    /**
     * 详细地址
     */
    private String address;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Integer workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public String getWorkProof() {
        return workProof;
    }

    public void setWorkProof(String workProof) {
        this.workProof = workProof;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "WorkReportForm [projectId=" + projectId + ", workTypeId=" + workTypeId + ", workContent=" + workContent + ", workProof=" + workProof + "]";
    }

}
