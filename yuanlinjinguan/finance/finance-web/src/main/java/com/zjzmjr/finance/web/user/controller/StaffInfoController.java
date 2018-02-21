package com.zjzmjr.finance.web.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.api.company.ICompanyDepartmentFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.model.company.CompanyDepartment;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.finance.web.user.form.PasswordChangeForm;
import com.zjzmjr.finance.web.user.form.StaffPersonForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 
 * 
 * @author oms
 * @version $Id: StaffInfoController.java, v 0.1 2017-8-9 下午8:46:34 oms Exp $
 */
@Controller
@RequestMapping("/user")
public class StaffInfoController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(StaffInfoController.class);

    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;

    @Resource(name = "adminFacade")
    private IAdminFacade adminFacade;
    
    @Resource(name = "departmentFacade")
    private ICompanyDepartmentFacade departmentFacade;

    /**
     * 员工详细
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value="/staffLogin.htm", method = RequestMethod.POST)
    public void staffLogin(ModelMap model, HttpServletResponse resp){
        try {
            StaffInfoQuery query = new StaffInfoQuery();
            query.setUserId(SpringSecurityUtil.getIntPrincipal());
            query.setPageBean(new PageBean(GenerateConstants.PAGE_SIZE, 1));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
            	// 公司外部人员信息一览
                ExternalPersonQuery personQuery = new ExternalPersonQuery();
                personQuery.setUserId(SpringSecurityUtil.getIntPrincipal());
                personQuery.setPageBean(new PageBean(GenerateConstants.PAGE_SIZE, 1));
                ResultPage<ExternalPersonInfo> personRst = staffPersonFacade.getExternalPersonByCondition(personQuery);
				if (personRst.isSuccess()) {
					model.put("data", personRst.getList());
					putSuccess(model, "");
				} else {
					putError(model, result.getErrorCode(), result.getErrorMsg());
				}
            }
        } catch (Exception ex) {
            logger.error("查询员工详细信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 查询通讯录一览
     * 
     * @param model
     * @param resp
     * @param page
     */
    @RequestMapping(value="/staffList.htm", method = RequestMethod.POST)
    public void dispStaffList(HttpServletResponse resp, StaffPersonForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try{
            StaffInfoQuery query = new StaffInfoQuery();
            // 在职的人员
            query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            query.setDepartmentId(form.getDepartmentId());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if (result.isSuccess()) {
                checkViewSelfEnable(result.getList());
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询通讯录一览失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 当前登录的人员是否对本人具有访问权限
     * 
     * @param staffList
     */
    private void checkViewSelfEnable(List<StaffBasicInfo> staffList){
        // 查找当前登录用户的信息
        StaffBasicInfo basicInfo = new StaffBasicInfo();
        for(StaffBasicInfo info : staffList){
            if(SpringSecurityUtil.getIntPrincipal().equals(info.getUserId())){
                basicInfo = info;
            }
            // 0 表示没有权限查看
            info.setViewEnable(GenerateConstants.number_0);
        }
        if (Util.isNull(basicInfo.getDepartId())) {
            // 当查询的部门不属于自己的部门的时候
            StaffInfoQuery query = new StaffInfoQuery();
            // 在职的人员
            query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            query.setUserId(SpringSecurityUtil.getIntPrincipal());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if (result.isSuccess()) {
                basicInfo = result.getList().get(0);
            }
        }
        // 判断是否是院长或副院长, 部门是院办的
        if(StringUtil.nullToInteger("1").equals(basicInfo.getDepartId())){
            for(StaffBasicInfo info : staffList){
                info.setViewEnable(GenerateConstants.number_1);
            }
        }else if(StringUtil.nullToInteger("2").equals(basicInfo.getDepartId())){
            // 经营部
            if(StringUtil.nullToInteger("3").equals(basicInfo.getUserInfo().getJobId())){
                for(StaffBasicInfo info : staffList){
                    if(StringUtil.nullToInteger("2").equals(info.getDepartId()) && 
                            !StringUtil.nullToInteger("3").equals(info.getUserInfo().getJobId())){
                        info.setViewEnable(GenerateConstants.number_1);
                    }else{
                        info.setViewEnable(GenerateConstants.number_0);
                    }
                }
            }
        }else if(StringUtil.nullToInteger("3").equals(basicInfo.getDepartId())){
            // 设计中心
            if(StringUtil.nullToInteger("5").equals(basicInfo.getUserInfo().getJobId())){
                // 设计总监
                for(StaffBasicInfo info : staffList){
                    if(StringUtil.nullToInteger("3").equals(info.getDepartId()) && 
                            !StringUtil.nullToInteger("5").equals(info.getUserInfo().getJobId())){
                        info.setViewEnable(GenerateConstants.number_1);
                    }else{
                        info.setViewEnable(GenerateConstants.number_0);
                    }
                }
            }
        }else if(StringUtil.nullToInteger("4").equals(basicInfo.getDepartId())){
            // 总师办
            if(StringUtil.nullToInteger("11").equals(basicInfo.getUserInfo().getJobId())){
                // 总工
                for(StaffBasicInfo info : staffList){
                    if(StringUtil.nullToInteger("4").equals(info.getDepartId()) && 
                            !StringUtil.nullToInteger("11").equals(info.getUserInfo().getJobId())){
                        info.setViewEnable(GenerateConstants.number_1);
                    }else{
                        info.setViewEnable(GenerateConstants.number_0);
                    }
                }
            }
        }
//        else if(StringUtil.nullToInteger("5").equals(basicInfo.getDepartId())){
//            // 财务部
//            if(StringUtil.nullToInteger("7").equals(basicInfo.getUserInfo().getJobId())){
//                // 总工
//                for(StaffBasicInfo info : staffList){
//                    if(StringUtil.nullToInteger("4").equals(info.getDepartId())){
//                        info.setViewEnable(GenerateConstants.number_1);
//                    }else{
//                        info.setViewEnable(GenerateConstants.number_0);
//                    }
//                }
//            }
//        }

        // 1 表示有权限查看  设置自己的权限
        basicInfo.setViewEnable(GenerateConstants.number_1);
    }
    
    /**
     * 员工详细
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value="/{staffId}/staff.htm", method = RequestMethod.POST)
    public void getStaffInfo(ModelMap model, HttpServletResponse resp, @PathVariable int staffId){
        try {
            StaffInfoQuery query = new StaffInfoQuery();
            query.setId(staffId);
            query.setPageBean(new PageBean(GenerateConstants.PAGE_SIZE, 1));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询员工详细信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 根据条件查询公司员工一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/companyStaffs.htm", method = RequestMethod.POST)
    public void getCompanyStaffInfo(HttpServletResponse resp, StaffPersonForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try{
            StaffInfoQuery query = new StaffInfoQuery();
            query.setUserName(form.getUserName());
            query.setDepartmentId(form.getDepartmentId());
            query.setJobId(form.getJobId());
            if(Util.isNotNull(form.getJobStatus())){
                query.setJobStatus(form.getJobStatus()); 
            }else{
                query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage() == null ? 1 : form.getPage()));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询员工详细信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 查询公司内部的正式员工及非正式的员工，
     * 返回的list中，第一个参数是公司的正式员工， 第二参数是公司的非正式员工
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/staffPersons.htm", method = RequestMethod.POST)
    public void getStaffPersonInfo(HttpServletResponse resp, StaffPersonForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            List<StaffBasicInfo> staffLst = new ArrayList<StaffBasicInfo>();
            // 公司内部员工信息一览
            StaffInfoQuery query = new StaffInfoQuery();
            query.setUserName(form.getUserName());
            query.setDepartmentId(form.getDepartmentId());
            query.setJobId(form.getJobId());
            query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            query.setPageBean(new PageBean(form.getRows(), form.getPage() == null ? 1 : form.getPage()));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if(result.isSuccess()){
                staffLst.addAll(result.getList());
            }
            // 公司外部人员信息一览
            ExternalPersonQuery personQuery = new ExternalPersonQuery();
            personQuery.setPersonnelNature(form.getPersonnelNature());
            personQuery.setStatus(1);
            personQuery.setPageBean(new PageBean(form.getRows(), form.getPage() == null ? 1 : form.getPage()));
            ResultPage<ExternalPersonInfo> personRst = staffPersonFacade.getExternalPersonByCondition(personQuery);
            if(personRst.isSuccess()){
                StaffBasicInfo basic = null;
                for(ExternalPersonInfo person : personRst.getList()){
                    basic = new StaffBasicInfo();
                    basic.setId(person.getId());
                    basic.setUserId(person.getUserId());
                    person.getUserInfo().setName("(H)"+person.getUserInfo().getName());
                    person.getUserInfo().setId(person.getUserId());
                    basic.setUserInfo(person.getUserInfo());
                    staffLst.add(basic);
                }
            }
            if (staffLst.isEmpty()) {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            } else {
                model.put("data", staffLst);
                putSuccess(model, "");
            }
        } catch (Exception ex) {
            logger.error("查询公司员工及外部人员一览信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 修改密码
     * 
     * @param resp
     * @param form
     * @param bindResult
     */
    @RequestMapping(value = "/changePassword.htm", method = RequestMethod.POST)
    public void modifyPassword(HttpServletResponse resp, @Valid PasswordChangeForm form, BindingResult bindResult) {
        Map<String, Object> model = new HashMap<String, Object>(4);
         if (!resolveBindingError(form, bindResult, model)) {
             try {
                 Result result = adminFacade.changePassword(SpringSecurityUtil.getIntPrincipal(),form.getOldPassword(), form.getNewPassword());
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
     * 查询部门名称和部门人员数量
     * 
     * @param resp
     */
    @RequestMapping(value = "/getStaffDepartmentAndCount.htm", method = RequestMethod.POST)
    public void getStaffDepartmentAndCount(HttpServletResponse resp){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartmentQuery query = new CompanyDepartmentQuery();
            query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            ResultRecord<CompanyDepartment> result = departmentFacade.getStaffDepartmentAndCount(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询部门名称和部门人员数量失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
