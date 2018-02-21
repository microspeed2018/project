package com.zjzmjr.finance.web.project.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: ReportConditionForm.java, v 0.1 2017-9-19 下午1:21:55 oms Exp $
 */
public class ReportConditionForm extends AbstractForm {

    /**  */
    private static final long serialVersionUID = 4014183136687667628L;

    private Integer id;

    private String timeStart;

    private String timeEnd;

    private String projectLeader;

    private String managePerson;

    private String projectStep;

    private Integer status;

    private String dispField;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getManagePerson() {
        return managePerson;
    }

    public void setManagePerson(String managePerson) {
        this.managePerson = managePerson;
    }

    public String getProjectStep() {
        return projectStep;
    }

    public void setProjectStep(String projectStep) {
        this.projectStep = projectStep;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDispField() {
        return dispField;
    }

    public void setDispField(String dispField) {
        this.dispField = dispField;
    }

    @Override
    public String toString() {
        return "ReportConditionForm [id=" + id + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", projectLeader=" + projectLeader + ", managePerson=" + managePerson + ", projectStep=" + projectStep + ", status=" + status + ", dispField=" + dispField + "]";
    }

}
