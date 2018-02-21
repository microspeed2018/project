package com.zjzmjr.admin.web.company.controller;

import java.io.PrintWriter;
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

import com.zjzmjr.admin.web.company.form.CompanyDepartmentForm;
import com.zjzmjr.core.api.company.ICompanyDepartmentFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.biz.weixin.JSONProcesserUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.company.CompanyDepartment;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyDepartmentStaff;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@RequestMapping("/department/user")
@Controller
public class CompanyDepartmentController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CompanyDepartmentController.class);

    private final static String index = "/WEB-INF/pages/company/department.jsp";

    @Resource(name = "companyDepartmentFacade")
    private ICompanyDepartmentFacade companyDepartmentFacade;
    
    /**
     * 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("departmentAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.DEPARTMENT_ADD));
        model.put("departmentUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.DEPARTMENT_UPDATE));
        model.put("departmentSetInvalidAuth", SpringSecurityUtil.hasAuth(UserAuthParams.DEPARTMENT_SET_INVALID));
        return index;
    }
    
    /**
     * 部门一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getDepartmentStaff.htm", method = RequestMethod.POST)
    public void getDepartmentStaff(HttpServletResponse resp, CompanyDepartmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartmentQuery query = new CompanyDepartmentQuery();
            if(Util.isNotNull(form.getId())){               
                query.setId(form.getId());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if (Util.isNotNull(form.getName())) {
                query.setName(form.getName());
            }
            if (Util.isNotNull(form.getDepartmentLeader())) {
                query.setDepartmentLeader(form.getDepartmentLeader());
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
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<CompanyDepartmentStaff> result = companyDepartmentFacade.getDepartmentStaff(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("部门查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 新增部门
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.DEPARTMENT_ADD }, checkSource = false)
    @RequestMapping(value = "/insertDepartment.htm", method = RequestMethod.POST)
    public void insertDepartment(HttpServletResponse resp, CompanyDepartmentForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartment query = new CompanyDepartment();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setName(form.getName());
            if(form.getDepartmentLeader()!=-1){
                query.setDepartmentLeader(form.getDepartmentLeader()); 
            }           
            query.setStatus(GenerateConstants.number_1);
            query.setCreateTime(new Date());
            query.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_DEPARTMENT.getValue());
            adminBusiness.setComment(AdminBusinessTypeEnum.B_INSERT_DEPARTMENT.getMessage());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyDepartmentFacade.insertCompanyDepartment(query);
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
            logger.error("部门新增出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 部门更新
     * 
     * @param resp
     * @param form
     */
    @Security(auth = {UserAuthParams.DEPARTMENT_SET_INVALID,UserAuthParams.DEPARTMENT_UPDATE}, checkSource = true)
    @RequestMapping(value = "/updateDepartment.htm", method = RequestMethod.POST)
    public void updateDepartment(HttpServletResponse resp, CompanyDepartmentForm form,HttpServletRequest req){
       Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartment query = new CompanyDepartment();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setName(form.getName());
            query.setDepartmentLeader(form.getDepartmentLeader()); 
            query.setStatus(form.getStatus());
            query.setCreateTime(new Date());
            query.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_DEPARTMENT.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_MODIFY_DEPARTMENT.getMessage());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = companyDepartmentFacade.updateCompanyDepartmentById(query);
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
            logger.error("部门更新出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 条件查询部门信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getDepartmentByCondition.htm", method = RequestMethod.POST)
    public void getDepartmentByCondition(HttpServletResponse resp, CompanyDepartmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartmentQuery query = new CompanyDepartmentQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setName(form.getName());
            query.setDepartmentLeader(form.getDepartmentLeader());
            query.setStatus(form.getStatus());
            ResultRecord<CompanyDepartment> result = companyDepartmentFacade.getCompanyDepartmentByCondition(query);
            if (result.isSuccess()) {
                if (Integer.valueOf(1).equals(form.getType())) {// 以流得形式返回结果
                    PrintWriter out = resp.getWriter();
                    resp.setCharacterEncoding("utf-8");
                    resp.setContentType("application/json; charset=utf-8");
                    out.print(JSONProcesserUtil.parseObj(result.getList()));
                    out.flush();
                    out.close();
                } else {
                    model.put("rows", result.getList());
                    putSuccess(model, "");
                }
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("条件查询部门信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
   
}
