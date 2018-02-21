/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.admin.web.common.util.JsonUtil;
import com.zjzmjr.admin.web.console.form.AdminCreateForm;
import com.zjzmjr.admin.web.console.form.AdminPageQueryForm;
import com.zjzmjr.admin.web.console.form.AdminUpdateForm;
import com.zjzmjr.admin.web.console.form.PasswordChangeForm;
import com.zjzmjr.admin.web.home.controller.AdminLoggerController;
import com.zjzmjr.common.json.DateJsonValueProcessor;
import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminAccStatusEnum;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.DepartmentEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminCreate;
import com.zjzmjr.core.model.admin.AdminQuery;
import com.zjzmjr.core.model.admin.AdminUpdate;
import com.zjzmjr.core.model.user.CompanyStaffPerson;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * ADMIN 控制器
 * 
 * @author elliott
 * @version $Id: AdminController.java, v 0.1 2014-1-15 下午1:20:57 elliott Exp $
 */
@Controller
@RequestMapping("/console/admin.htm")
public class AdminController extends AdminLoggerController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final String queryView = "/WEB-INF/pages/console/admin/account_query.jsp";

    private final String resetPwdView = "/WEB-INF/pages/console/admin/user_password.jsp";

    /**
     * 密码修改视图
     */
    private static final String PWD_VIEW = "/WEB-INF/pages/console/admin/psw.jsp";
    
    @Autowired
    private IAdminFacade adminFacade;
    
    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;

    /**
     * admin查询视图
     * 
     * @param model
     * @return
     */
    // @Security(auth = "CONSOLE_ADMIN_QUERY", view =
    // "redirect:/unauthorized.htm")
    @RequestMapping(method = RequestMethod.GET)
    public String queryAdminView(ModelMap model) {
        model.put("statusEnums", AdminAccStatusEnum.values());
        model.put("departmentEnums", DepartmentEnum.values());
        model.put("hasAuthGrantAuth", true);
        model.put("hasMenuGrantAuth", true);
        model.put("hasUpdateAuth", true);
        model.put("adminAddAuth", true);
//        CreditBankQuery query = new CreditBankQuery();
//        PageBean pageBean = new PageBean(Integer.MAX_VALUE, 1);
//        query.setPageBean(pageBean);
//        model.put("creditBank", creditBankFacade.getCreditBanksInfo(query).getList());
        // model.put("hasAuthGrantAuth",
        // SpringSecurityUtil.hasAuth(ConsolePermissionEnum.CONSOLE_ADMIN_AUTH_GRANT.getValue()));
        // model.put("hasMenuGrantAuth",
        // SpringSecurityUtil.hasAuth(ConsolePermissionEnum.CONSOLE_ADMIN_MENU_GRANT.getValue()));
        // model.put("hasUpdateAuth",
        // SpringSecurityUtil.hasAuth(ConsolePermissionEnum.CONSOLE_ADMIN_UPDATE.getValue()));
        // model.put("adminAddAuth",
        // SpringSecurityUtil.hasAuth(ConsolePermissionEnum.CONSOLE_ADMIN_ADD.getValue()));
        return queryView;
    }

    /**
     * 密码管理视图
     * 
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, params = "action=pwdView")
    public String home(ModelMap model) {
        return PWD_VIEW;
    }
    
    /**
     * 分页查询管理员
     * 
     * @param resp
     * @param rows
     * @param page
     * @param form
     * @param bindResult
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = { "action=queryByPage" })
    public void queryAdminByPage(HttpServletResponse resp, @RequestParam(required = false)
    Integer rows, @RequestParam(required = false)
    Integer page, AdminPageQueryForm form, BindingResult bindResult) {
        logger.debug("queryAdminByPage 执行开始  入参:{}", form);
        JSONObject model = new JSONObject();
        if (!resolveBindingError(form, bindResult, model)) {
            try {
                ResultPage<Admin> result = adminFacade.queryByPage(convertQuery(form, page, rows));
                logger.debug("queryAdminByPage 执行结束  出参:{}", result);
                JsonConfig config = getJsonConfig("yy-MM-dd HH:mm:ss");
                config.registerJsonValueProcessor("userViewDate", new DateJsonValueProcessor("MM-dd"));
                config.setExcludes(new String[] { "password", "version", });
                model.put("total", result.getPage().getTotalResults());
                model.accumulate("rows", result.getList(), config);
                putSuccess(model, "");
            } catch (IllegalArgumentException e) {
                putError(model, e.getMessage());
            } catch (Exception e) {
                putError(model, "用户查询出错");
                logger.error("查询用户出错", e);
            }

            responseAsJson(model, resp);
        }
    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST },params = { "action=getAllAdminUser" })
    public void getAllAdminUser(HttpServletResponse resp) throws Exception {
        logger.debug("getAllAdminUser 执行开始  入参:{}");
        PageBean pageBean = new PageBean(Integer.MAX_VALUE, 1);
        AdminQuery query = new AdminQuery();
        query.setPageBean(pageBean);
        ResultPage<Admin> result = adminFacade.queryByPage(query);
        logger.debug("getAllAdminUser 执行结束  出参:{}", result);
        if (result != null && result.isSuccess()) {
            JsonUtil.printJSON(result.getList(), resp);
            return;
        }
        JsonUtil.printJSON("", resp);
    }

    private AdminQuery convertQuery(AdminPageQueryForm form, Integer page, Integer rows) {
        PageBean pageBean = new PageBean(rows, page);
        AdminQuery query = new AdminQuery();
        query.setId(form.getId());
        query.setUsername(StringUtils.trimToNull(form.getUsername()));
        query.setName(StringUtils.trimToNull(form.getName()));
        query.setAccStatus(form.getStatus());
        query.setMobile(StringUtils.trimToNull(form.getMobile()));
        query.setDepartment(form.getDepartment());
        query.setEmail(StringUtils.trimToNull(form.getEmail()));

        query.setPageBean(pageBean);
        return query;
    }

    /**
     * 创建帐户
     * 
     * @param model
     * @param resp
     * @param form
     * @param bindResult
     */
    @Security(auth = "CONSOLE_ADMIN_ADD", checkSource = true)
    @RequestMapping(method = RequestMethod.POST, params = { "action=createAccount" })
    public void createAccount(ModelMap model, HttpServletResponse resp, AdminCreateForm form, BindingResult bindResult,HttpServletRequest req) {
        logger.debug("createAccount 执行开始  入参:{}", form);
        if (!resolveBindingError(form, bindResult, model)) {
            try {
                AdminAccStatusEnum status = AdminAccStatusEnum.getByValue(form.getAccStatus());
                Assert.notNull(status, form.resolveFiled("accStatus") + "不能为空");
                DepartmentEnum department = DepartmentEnum.getByValue(form.getDepartment());
                Assert.notNull(department, form.resolveFiled("department") + "不能为空");
                Assert.isTrue(StringUtils.isNotBlank(form.getName()), form.resolveFiled("name") + "不能为空");
                Assert.isTrue(StringUtils.isNotBlank(form.getPassword()), form.resolveFiled("password") + "不能为空");
                //新增管理员事物
                AdminBusiness adminBusiness=new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_USER.getValue());
                ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
                if(GenerateConstants.number_1.equals(form.getIsEmployee())){
                    Assert.isTrue(StringUtils.isNotBlank(form.getMobile()), form.resolveFiled("mobile") + "不能为空");
                    // 检查该用户是否已经存在                
                    ResultEntry<Admin> result = adminFacade.getByMobile(form.getMobile());                    
                    if(!result.isSuccess() && 
                            ErrorCodeEnum.RECORD_NOT_EXSIST.getCode().equals(result.getErrorCode())){
                        AdminCreate dto = new AdminCreate();
                        dto.setAccStatus(status);
                        dto.setCreateUser(0);
                        dto.setDepartment(department);
                        dto.setJobId(form.getJobId());
                        dto.setCompanyId(SpringSecurityUtil.getIntCompany());
                        dto.setEmail(StringUtils.trimToNull(form.getEmail()));
                        dto.setMobile(StringUtils.trimToNull(form.getMobile()));
                        dto.setName(StringUtils.trimToNull(form.getName()));
                        dto.setPassword(StringUtils.trimToNull(form.getPassword()));
                        dto.setSecurityIp(StringUtils.trimToNull(form.getSecurityIp()));
                        dto.setUsername(StringUtils.trimToNull(form.getMobile()));
                        CompanyStaffPerson companyStaffPerson = new CompanyStaffPerson();
                        if(GenerateConstants.number_1.equals(form.getIsEmployee())){
                          //查询有无相同员工编号
                            StaffInfoQuery staffQuery = new StaffInfoQuery();
                            staffQuery.setEmployeeNo(form.getEmployeeNo());
                            PageBean bean = new PageBean(Integer.MAX_VALUE, 1);
                            staffQuery.setPageBean(bean);
                            ResultPage<StaffBasicInfo> results = staffPersonFacade.getStaffInfoByCondition(staffQuery);
                            if(!results.isSuccess()){
                                StaffBasicInfo staffInfo = new StaffBasicInfo();
                                staffInfo.setJobStatus(form.getJobStatus());
                                staffInfo.setTelephone(form.getTelephone());
                                staffInfo.setShortTelephone(form.getShortTelephone());
                                staffInfo.setEmail(form.getEmail());
                                staffInfo.setEmployeeNo(form.getEmployeeNo());
                                staffInfo.setVirtualMobile(form.getVirtualCornet());
                                staffInfo.setIdentityNo(form.getIdentityNo());
                                staffInfo.setSex(form.getSex());
                                staffInfo.setBirthday(form.getBirthday().replace("/", ""));
                                if(form.getStaffType()!=-1){
                                    staffInfo.setStaffType(form.getStaffType());
                                }
                                staffInfo.setEntryDate(form.getEntryDate().replace("/", ""));
                                staffInfo.setEntranceGuardCardNumber(form.getEntranceGuardCardNumber());
                                if(form.getPoliticalStatus()!=-1){
                                    staffInfo.setPoliticalStatus(form.getPoliticalStatus()); 
                                }
                                staffInfo.setTitleQuality(form.getTitleQuality());
                                staffInfo.setGraduateInstitutions(form.getGraduateInstitutions());
                                staffInfo.setStudyMajor(form.getStudyMajor());
                                if(form.getEducation()!=-1){
                                    staffInfo.setEducation(form.getEducation());  
                                }
                                staffInfo.setFirstWorkDate(form.getFirstWorkDate().replace("/", ""));
                                staffInfo.setRegisteredResidence(form.getRegisteredResidence());
                                staffInfo.setPresentAddress(form.getPresentAddress());
                                staffInfo.setContract1(form.getContract1());
                                staffInfo.setContract2(form.getContract2());
                                staffInfo.setContract3(form.getContract3());
                                staffInfo.setContractDueDate(form.getContractDueDate().replace("/", ""));
                                staffInfo.setSocialSecurityBase(form.getSocialSecurityBase());
                                staffInfo.setBank(form.getBank());
                                staffInfo.setBankcardNum(form.getBankcardNum());
                                staffInfo.setMemo(form.getMemo());
                                staffInfo.setCreateTime(new Date());
                                staffInfo.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                                List<StaffBasicInfo> list = new ArrayList<StaffBasicInfo>();
                                list.add(staffInfo);
                                companyStaffPerson.setStaff(list);
                                adminBusiness.setExtend1("创建人员"); 
                                adminFacade.create(dto,companyStaffPerson);
                                putSuccess(model, "添加成功");
                                logAdmin("创建账户，account:{}", dto);
                                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue()); 
                            }else{
                                putError(model, "该员工编号已经存在");
                                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                                adminBusiness.setComment("该员工编号已经存在");
                            }
                        }     
                    }else if(!result.isSuccess()){
                        putError(model, result.getErrorMsg());
                        adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                        adminBusiness.setComment(result.getErrorMsg());
                    }else{
                        putError(model, "该员工手机号已经存在");
                        adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                        adminBusiness.setComment("该员工手机号已经存在");
                    }
                }else{
                    if(Util.isNotNull(form.getMobile())){
                        // 检查该用户是否已经存在                
                        ResultEntry<Admin> result = adminFacade.getByMobile(form.getMobile());                    
                        if(!result.isSuccess() && 
                                ErrorCodeEnum.RECORD_NOT_EXSIST.getCode().equals(result.getErrorCode())){
                            AdminCreate dto = new AdminCreate();
                            dto.setAccStatus(status);
                            dto.setCreateUser(0);
                            dto.setDepartment(department);
                            dto.setJobId(form.getJobId());
                            dto.setCompanyId(SpringSecurityUtil.getIntCompany());
                            dto.setEmail(StringUtils.trimToNull(form.getEmail()));
                            dto.setMobile(StringUtils.trimToNull(form.getMobile()));
                            dto.setName(StringUtils.trimToNull(form.getName()));
                            dto.setPassword(StringUtils.trimToNull(form.getPassword()));
                            dto.setSecurityIp(StringUtils.trimToNull(form.getSecurityIp()));
                            dto.setUsername(StringUtils.trimToNull(form.getMobile()));
                            CompanyStaffPerson companyStaffPerson = new CompanyStaffPerson();
                            ExternalPerson externalPerson = new ExternalPerson();
                            externalPerson.setCompany(form.getCompany());
                            externalPerson.setJob(form.getJob());
                            externalPerson.setPersonnelNature(form.getPersonnelNature());
                            externalPerson.setMobile(form.getMobile());
                            externalPerson.setEmail(form.getEmail());
                            externalPerson.setEmployeeNo(form.getEmployeeNo());
                            externalPerson.setMemo(form.getMemo());
                            externalPerson.setRelatedPerson(form.getRelatedPerson());
                            externalPerson.setStatus(form.getJobStatus());
                            externalPerson.setCreateTime(new Date());
                            externalPerson.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                            List<ExternalPerson> externalLst = new ArrayList<ExternalPerson>();
                            externalLst.add(externalPerson);
                            companyStaffPerson.setPerson(externalLst);
                            adminBusiness.setExtend1("创建外部人员");
                            adminFacade.create(dto,companyStaffPerson);
                            putSuccess(model, "添加成功");
                            logAdmin("创建账户，account:{}", dto);
                            adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());                            
                        }else if(!result.isSuccess()){
                            putError(model, result.getErrorMsg());
                            adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                            adminBusiness.setComment(result.getErrorMsg());
                        }else{
                            putError(model, "该员工手机号已经存在");
                            adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                            adminBusiness.setComment("该员工手机号已经存在");
                        }
                    }else{
                        AdminCreate dto = new AdminCreate();
                        dto.setAccStatus(status);
                        dto.setCreateUser(0);
                        dto.setDepartment(department);
                        dto.setJobId(form.getJobId());
                        dto.setCompanyId(SpringSecurityUtil.getIntCompany());
                        dto.setEmail(StringUtils.trimToNull(form.getEmail()));
                        dto.setMobile(StringUtils.trimToNull(form.getMobile()));
                        dto.setName(StringUtils.trimToNull(form.getName()));
                        dto.setPassword(StringUtils.trimToNull(form.getPassword()));
                        dto.setSecurityIp(StringUtils.trimToNull(form.getSecurityIp()));
                        dto.setUsername(StringUtils.trimToNull(form.getMobile()));
                        CompanyStaffPerson companyStaffPerson = new CompanyStaffPerson();
                        ExternalPerson externalPerson = new ExternalPerson();
                        externalPerson.setCompany(form.getCompany());
                        externalPerson.setJob(form.getJob());
                        externalPerson.setPersonnelNature(form.getPersonnelNature());
                        externalPerson.setMobile(form.getMobile());
                        externalPerson.setEmail(form.getEmail());
                        externalPerson.setEmployeeNo(form.getEmployeeNo());
                        externalPerson.setMemo(form.getMemo());
                        externalPerson.setRelatedPerson(form.getRelatedPerson());
                        externalPerson.setStatus(form.getJobStatus());
                        externalPerson.setCreateTime(new Date());
                        externalPerson.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                        List<ExternalPerson> externalLst = new ArrayList<ExternalPerson>();
                        externalLst.add(externalPerson);
                        companyStaffPerson.setPerson(externalLst);
                        adminBusiness.setExtend1("创建外部人员");
                        adminFacade.create(dto,companyStaffPerson);
                        putSuccess(model, "添加成功");
                        logAdmin("创建账户，account:{}", dto);
                        adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue()); 
                    }                    
                }                              
                //更新管理员事物
                adminBusiness.setId(val.getT().getId());
                AdminTransactionUtil.updateAdminTransaction(adminBusiness);
            } catch (IllegalArgumentException e) {
                putError(model, e.getMessage());
            } catch (Exception e) {
                putError(model, e.getMessage());
                logger.error("创建用户出错", e);
            }
        }
        logger.debug("createAccount 执行结束  出参:{}", model);
        responseResultAsJson(model, resp);
    }

    /**
     * 修改用户帐户
     * 
     * @param resp
     * @param form
     * @param bindResult
     */
    @Security(auth = "CONSOLE_ADMIN_UPDATE", checkSource = true)
    @RequestMapping(method = RequestMethod.POST, params = { "action=updateAdmin" })
    public void updateAdmin(HttpServletResponse resp, AdminUpdateForm form, BindingResult bindResult,HttpServletRequest req) {
        logger.debug("updateAdmin 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>(5);
        if (!resolveBindingError(form, bindResult, model)) {
            AdminUpdate update = new AdminUpdate();
            update.setDepartment(DepartmentEnum.getByValue(form.getDepartment()));
            update.setCompanyId(form.getCompanyId());
            update.setEmail(form.getEmail());
            update.setId(form.getId());
            update.setMobile(form.getMobile());
            update.setName(form.getName());
            update.setSecurityIp(form.getSecurityIp());
            update.setStatus(AdminAccStatusEnum.getByValue(form.getStatus()));
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_USER_ACCOUNT.getValue());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            try {
                adminFacade.update(update);
                putSuccess(model, "");
                logAdmin("修改账户信息,update:{}", update);
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } catch (IllegalArgumentException e) {
                putError(model, e.getMessage());
            } catch (Exception e) {
                putError(model, "更新用户信息出错");
                logger.error("更新用户信息出错,{}", form, e);
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("更新用户信息出错");
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        }
        logger.debug("updateAdmin 执行结束  出参:{}", model);
        responseAsJson(model, resp);
    }

    /**
     * 修改密码
     * 
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, params = "action=userPasswordView")
    public String modifyPasswordHome(ModelMap model) {
         model.put("user", adminFacade.getByID(SpringSecurityUtil.getIntPrincipal()).getT());
         model.put("departmentEnums", DepartmentEnum.values());
        return resetPwdView;
    }

    /**
     * 修改密码
     * 
     * @param resp
     * @param form
     * @param bindResult
     */
    // @Security(checkSource = true)
    @RequestMapping(method = RequestMethod.POST, params = { "action=changePassword" })
    public void modifyPassword(HttpServletResponse resp, @Valid PasswordChangeForm form, BindingResult bindResult) {
        logger.debug("modifyPassword 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>(4);
         if (!resolveBindingError(form, bindResult, model)) {
             try {
                 Result result = adminFacade.changePassword(SpringSecurityUtil.getIntPrincipal(),form.getOldPassword(), form.getNewPassword());
                 logger.debug("modifyPassword 执行结束  出参:{}", result);
                 if (!result.isSuccess()){
                	 putError(model, result.getErrorMsg());
                	 responseAsJson(model, resp);
                	 return;
                 }
                 putSuccess(model, "");
             } catch (Exception e) {
                 logger.error("用户修改密码出错,username:{}", SpringSecurityUtil.getUserName(), e);
                 putError(model, "用户修改密码出错");
             }
         }
        responseAsJson(model, resp);
    }
    
    /**
     * 
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=getPswByUsername" })
    public void getByUsername(ModelMap model, HttpServletResponse resp) {
        logger.debug("getByUsername 执行开始  入参:{}", model);
        try {
            ResultEntry<Admin> result = adminFacade.getByUsername(SpringSecurityUtil.getUserName());
            logger.debug("getByUsername 执行结束  出参:{}", result);
            if(result.isSuccess()){
                model.put("data", result.getT().getPassword());
                putSuccess(model, "");
            }else
                putError(model, result.getErrorMsg());
        } catch (Exception ex) {
            logger.error("用户信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
}
