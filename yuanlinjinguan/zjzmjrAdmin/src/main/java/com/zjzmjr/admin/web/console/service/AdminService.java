package com.zjzmjr.admin.web.console.service;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjzmjr.admin.web.console.form.AdminPageQueryForm;
import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.model.admin.Admin;

@Service
public class AdminService {
	public JSONObject queryForPage(AdminPageQueryForm form, int page, int rows) {
		return null;
	}

	private Admin convertAdmin(AdminPageQueryForm form) {
		Admin query = new Admin();
		query.setId(form.getId());
		query.setUsername(StringUtils.trimToNull(form.getUsername()));
		query.setName(StringUtils.trimToNull(form.getName()));
		query.setAccStatus(form.getStatus());
		query.setMobile(StringUtils.trimToNull(form.getMobile()));
		query.setDepartment(form.getDepartment());
		query.setEmail(StringUtils.trimToNull(form.getEmail()));
		return query;
	}
}
