package com.zjzmjr.admin.web.company.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.admin.web.company.form.CompanyDepartmentForm;
import com.zjzmjr.core.api.company.ICompanyBasicInfoFacade;
import com.zjzmjr.core.api.company.ICompanyDepartmentFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.company.CompanyDepartmentJob;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyJob;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@RequestMapping("/job/user")
@Controller
public class CompanyJobController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CompanyJobController.class);

    private final static String index = "/WEB-INF/pages/company/job.jsp";

    @Resource(name = "companyDepartmentFacade")
    private ICompanyDepartmentFacade companyDepartmentFacade;

    @Resource(name = "companyBasicInfoFacade")
    private ICompanyBasicInfoFacade companyBasicInfoFacade;

    /**
     * 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model, @RequestParam(value="action", required=false) String action) {
        if("auth".equals(action)){
            model.put("hasAuth", true);
        }else{
            model.put("hasAuth", false);
        }
        model.put("action", action);
        model.put("jobAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.JOB_ADD));
        model.put("jobUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.JOB_UPDATE));
        model.put("jobSetInvalidAuth", SpringSecurityUtil.hasAuth(UserAuthParams.JOB_SET_INVALID));
        return index;
    }

    /**
     * 职位一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getjobDepartment.htm", method = RequestMethod.POST)
    public void getjobDepartment(HttpServletResponse resp, CompanyDepartmentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartmentQuery query = new CompanyDepartmentQuery();
            if (Util.isNotNull(form.getId())) {
                query.setId(form.getId());
            }
            if (Util.isNotNull(form.getName())) {
                query.setName(form.getName());
            }
            if (Util.isNotNull(form.getDepartmentId())) {
                query.setDepartmentId(form.getDepartmentId());
            }
            if (Util.isNotNull(form.getStatus())) {
                query.setStatus(form.getStatus());
            }
            if (Util.isNotNull(form.getCreateTimeStart())) {
                query.setCreateTimeStart(form.getCreateTimeStart());
            }
            if (Util.isNotNull(form.getCreateTimeEnd())) {
                query.setCreateTimeEnd(form.getCreateTimeEnd());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setDepartmentStatus(GenerateConstants.number_1);
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<CompanyDepartmentJob> result = companyDepartmentFacade.getDepartmentJobInfo(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("职位查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 包括内部和外部所有职位的一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getInOutDepartment.htm", method = RequestMethod.POST)
    public void getInOutDepartment(HttpServletResponse resp, CompanyDepartmentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartmentQuery query = new CompanyDepartmentQuery();
            if (Util.isNotNull(form.getId())) {
                query.setId(form.getId());
            }
            if (Util.isNotNull(form.getName())) {
                query.setName(form.getName());
            }
            if (Util.isNotNull(form.getDepartmentId())) {
                query.setDepartmentId(form.getDepartmentId());
            }
            if (Util.isNotNull(form.getStatus())) {
                query.setStatus(form.getStatus());
            }
            if (Util.isNotNull(form.getCreateTimeStart())) {
                query.setCreateTimeStart(form.getCreateTimeStart());
            }
            if (Util.isNotNull(form.getCreateTimeEnd())) {
                query.setCreateTimeEnd(form.getCreateTimeEnd());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setDepartmentStatus(GenerateConstants.number_1);
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<CompanyDepartmentJob> result = companyDepartmentFacade.getInnerOuterDepartmentInfo(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("职位查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 新增职位
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.JOB_ADD }, checkSource = false)
    @RequestMapping(value = "/insertJob.htm", method = RequestMethod.POST)
    public void insertJob(HttpServletResponse resp, CompanyDepartmentForm form,HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyJob query = new CompanyJob();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setName(form.getName());
            if(Util.isNotNull(form.getDepartmentId())){
                query.setDepartmentId(form.getDepartmentId());
            }
            query.setStatus(GenerateConstants.number_1);
            query.setCreateTime(new Date());
            query.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_JOB.getValue());
            adminBusiness.setComment(AdminBusinessTypeEnum.B_INSERT_JOB.getMessage());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyDepartmentFacade.insertCompanyJob(query);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setExtend1(result.getErrorMsg());
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("职位新增出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 职位更新
     * 
     * @param resp
     * @param form
     */
    @Security(auth = {UserAuthParams.JOB_SET_INVALID,UserAuthParams.JOB_UPDATE}, checkSource = true)
    @RequestMapping(value = "/updateJob.htm", method = RequestMethod.POST)
    public void updateJob(HttpServletResponse resp, CompanyDepartmentForm form,HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyJob query = new CompanyJob();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setName(form.getName());
            query.setDepartmentId(form.getDepartmentId());
            query.setStatus(form.getStatus());
            query.setCreateTime(new Date());
            query.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_JOB.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_MODIFY_JOB.getMessage());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyDepartmentFacade.updateCompanyJobById(query);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("职位更新出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 条件查询职位信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getJobByCondition.htm", method = RequestMethod.POST)
    public void getJobByCondition(HttpServletResponse resp, CompanyDepartmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartmentQuery query = new CompanyDepartmentQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setName(form.getName());
            query.setDepartmentId(form.getDepartmentId());
            query.setStatus(form.getStatus());
            ResultRecord<CompanyJob> result = companyDepartmentFacade.getCompanyJobByCondition(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("条件查询职位信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
