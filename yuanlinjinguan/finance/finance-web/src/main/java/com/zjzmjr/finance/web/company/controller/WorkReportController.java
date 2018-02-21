package com.zjzmjr.finance.web.company.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.company.ICompanyInfoFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.company.WorkReport;
import com.zjzmjr.core.model.company.WorkReportInfo;
import com.zjzmjr.core.model.company.WorkReportQuery;
import com.zjzmjr.finance.web.company.form.WorkReportForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 工作汇报
 * 
 * @author oms
 * @version $Id: WorkReportController.java, v 0.1 2017-8-10 下午2:11:00 oms Exp $
 */
@Controller
@RequestMapping("/company/user")
public class WorkReportController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(WorkReportController.class);

    @Resource(name = "companyInfoFacade")
    private ICompanyInfoFacade companyInfoFacade;
    
    /**
     * 查询员工的工作报告
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/{userId}/workReport.htm", method = RequestMethod.POST)
    public void staffWorkReport(HttpServletResponse resp, @PathVariable int userId, WorkReportForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            WorkReportQuery query = new WorkReportQuery();
            if (userId == 0) {
                // 根据项目来查
                query.setProjectId(form.getProjectId());
            } else {
                query.setUserId(userId);
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<WorkReportInfo> result = companyInfoFacade.getWorkReportByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询工作报告失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 员工汇报工作报告
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/reportWork.htm", method = RequestMethod.POST)
    public void workReport(HttpServletResponse resp, WorkReportForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
//            Assert.isTrue(Util.isNotNull(form.getWorkTypeId()), "请选择工作类型");
            
            WorkReport record = new WorkReport();
            record.setUserId(SpringSecurityUtil.getIntPrincipal());
            record.setProjectId(form.getProjectId());
            record.setWorkTypeId(form.getWorkTypeId());
            record.setWorkContent(form.getWorkContent());
            record.setWorkProof(form.getWorkProof());
            record.setAddress(form.getAddress());
            record.setCreateTime(new Date());
            record.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            record.setUpdateTime(record.getCreateTime());
            record.setUpdateUserId(record.getCreateUserId());
            ResultEntry<Integer> result = companyInfoFacade.insertWorkReport(record);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("插入工作报告失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
}
