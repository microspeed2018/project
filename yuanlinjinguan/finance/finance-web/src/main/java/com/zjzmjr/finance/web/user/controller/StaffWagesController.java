package com.zjzmjr.finance.web.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.user.IStaffWagesFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.user.StaffWagesInfo;
import com.zjzmjr.core.model.user.StaffWagesQuery;
import com.zjzmjr.finance.web.user.form.StaffWagesForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@RequestMapping("/staffWages/user")
@Controller
public class StaffWagesController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StaffWagesController.class);

    @Resource(name = "staffWagesFacade")
    private IStaffWagesFacade staffWagesFacade;
    
    /**
     * 薪酬记录一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/queryStaffWages.htm", method = RequestMethod.POST)
    public void queryStaffWages(HttpServletResponse resp, StaffWagesForm form) {
        logger.debug("queryStaffWages 执行开始");
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            StaffWagesQuery query = new StaffWagesQuery();
            query.setUserId(SpringSecurityUtil.getIntPrincipal());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<StaffWagesInfo> result = staffWagesFacade.queryStaffWages(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }

        } catch (Exception ex) {
            logger.error("查询薪酬信息失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("queryStaffWages 执行结束");
        responseAsJson(model, resp);
    }
    
    /**
     * 根据id获取薪酬记录详情
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/queryStaffWagesById.htm", method = RequestMethod.POST)
    public void queryStaffWagesById(HttpServletResponse resp, StaffWagesForm form) {
        logger.debug("queryStaffWagesById 执行开始");
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(Util.isNotNull(form.getId()),"id不能为空！");
            ResultEntry<StaffWagesInfo> result = staffWagesFacade.getStaffWageById(form.getId());
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }

        } catch (Exception ex) {
            logger.error("查询薪酬信息失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("queryStaffWagesById 执行结束");
        responseAsJson(model, resp);
    }
}
