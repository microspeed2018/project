package com.zjzmjr.finance.web.project.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 项目一览展示条件表
 * 
 * @author oms
 * @version $Id: DisplayRuleForm.java, v 0.1 2017-9-4 下午6:54:54 oms Exp $
 */
public class DisplayRuleForm extends AbstractForm {

	/**  */
	private static final long serialVersionUID = -6778733484045151469L;

	/**
	 * 项目编号
	 */
	private Integer projectId;

	/**
	 * 1 倒序 2 升序
	 */
	private Integer timeOrder;

	private Integer projectLeader;

	private Integer managePerson;

	/**
	 * 项目阶段,可以多个，逗号分隔
	 */
	private String projectStep;

	private Integer status;

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

	public Integer getTimeOrder() {
		return timeOrder;
	}

	public void setTimeOrder(Integer timeOrder) {
		this.timeOrder = timeOrder;
	}

	public Integer getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(Integer projectLeader) {
		this.projectLeader = projectLeader;
	}

	public Integer getManagePerson() {
		return managePerson;
	}

	public void setManagePerson(Integer managePerson) {
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

	@Override
	public String toString() {
		return "DisplayRuleForm [projectId=" + projectId + ", timeOrder="
				+ timeOrder + ", projectLeader=" + projectLeader
				+ ", managePerson=" + managePerson + ", projectStep="
				+ projectStep + ", status=" + status + "]";
	}

}
