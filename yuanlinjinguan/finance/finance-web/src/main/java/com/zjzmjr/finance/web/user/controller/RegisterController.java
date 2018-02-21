package com.zjzmjr.finance.web.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.common.util.VerifyUtil;
import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.common.util.SmsUtil;
import com.zjzmjr.core.enums.admin.AdminAccStatusEnum;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.DepartmentEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminCreate;
import com.zjzmjr.core.model.user.CompanyStaffPerson;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.finance.web.user.form.AdminCreateForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.security.web.util.WebUtil;
import com.zjzmjr.web.mvc.controller.BaseController;


/**
 * 用户注册
 * 
 * @author lenovo
 * @version $Id: RegisterController.java, v 0.1 2017-12-1 下午2:54:12 lenovo Exp $
 */

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    
    @Resource(name = "adminFacade")
    private IAdminFacade adminFacade;

    /**
     * 创建帐户
     * 
     * @param model
     * @param resp
     * @param form
     * @param bindResult
     */
    @RequestMapping(value="/register.htm", method=RequestMethod.POST)
    public void createAccount(ModelMap model, HttpServletResponse resp, AdminCreateForm form, BindingResult bindResult,HttpServletRequest req) {
        logger.debug("createAccount 执行开始  入参:{}", form);
        if (!resolveBindingError(form, bindResult, model)) {
            try {
                AdminAccStatusEnum status = AdminAccStatusEnum.getByValue(form.getAccStatus());
                Assert.notNull(status, form.resolveFiled("accStatus") + "不能为空");
                DepartmentEnum department = DepartmentEnum.getByValue(form.getDepartment());
                Assert.notNull(department, form.resolveFiled("department") + "不能为空");
                //Assert.isTrue(StringUtils.isNotBlank(form.getName()), form.resolveFiled("name") + "不能为空");
                Assert.isTrue(StringUtils.isNotBlank(form.getPassword()), form.resolveFiled("password") + "不能为空");
                Assert.isTrue(StringUtils.isNotBlank(form.getMobile()), form.resolveFiled("mobile") + "不能为空");
                String checkCode = req.getParameter("checkcode");
                Assert.isTrue(!StringUtils.isEmpty(checkCode), "请输入手机验证码");
                String tmpCheckCode = SmsUtil.getMobileCheckCode(SmsUtil.CODE_KEY_REGISTER, form.getMobile());// 获取已发送手机端的验证码
                Assert.isTrue(!StringUtils.isEmpty(tmpCheckCode), "验证码已失效");// 比较输入的验证码和发送至手机端的验证码
                Assert.isTrue(tmpCheckCode.equals(checkCode), "验证码错误");
                // 检查该用户是否已经存在
                ResultEntry<Admin> result = adminFacade.getByMobile(form.getMobile());
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_USER.getValue());
                ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
                if (!result.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getCode().equals(result.getErrorCode())) {
                    AdminCreate dto = new AdminCreate();
                    dto.setAccStatus(status);
                    dto.setCreateUser(0);
                    dto.setDepartment(department);
                    dto.setJobId(form.getJobId());
                    dto.setCompanyId(form.getCompanyId());
                    dto.setEmail(StringUtils.trimToNull(form.getEmail()));
                    dto.setMobile(StringUtils.trimToNull(form.getMobile()));
                    dto.setName(StringUtil.null2Str(form.getName()));
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
                    adminFacade.create(dto, companyStaffPerson);
                    putSuccess(model, "添加成功");
                    //logAdmin("创建账户，account:{}", dto);
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
     * 
     * 获取手机验证码
     * @param model
     * @param req
     * @param resp
     */
    @RequestMapping(value="/sendcode.htm", method={RequestMethod.POST, RequestMethod.GET})
    public void MobileCode(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String mobile = req.getParameter("mobile");
            Assert.isTrue(!StringUtils.isEmpty(mobile), "请输入手机号码");
            Assert.isTrue(VerifyUtil.isMobile(mobile), "请输入正确的手机号码");
            PointSymbol pointSymbol = new PointSymbol();
            pointSymbol.setCode(SmsUtil.getMobileCode(SmsUtil.CODE_KEY_REGISTER, mobile, WebUtil.getUserIp(req), SmsUtil.LIFE_TIME_FIVE_MIN));// 验证码
            pointSymbol.setTemplateCode(MessageContextEnum.userRegisterCode.getTemplateCode());
            pointSymbol.setMobile(mobile);
            SmsUtil.sendSMS(pointSymbol);
            putSuccess(model, "");
        } catch (Exception ex) {
            logger.error("获取手机验证码失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("MobileCode出参:{}", model);
        responseAsJson(model, resp);
    }
    
    /**
     * 找回密码
     * 
     * @param model
     * @param req
     * @param resp
     */
    @RequestMapping(value="/findPassword.htm", method=RequestMethod.POST)
    public void resetPassWord(ModelMap model, HttpServletRequest req, HttpServletResponse resp){
        try{            
            String mobile = req.getParameter("mobile");
            Assert.isTrue(!StringUtils.isEmpty(mobile), "请输入手机号码");
            Assert.isTrue(VerifyUtil.isMobile(mobile), "请输入正确的手机号码");
            String checkCode = req.getParameter("checkcode");
            Assert.isTrue(!StringUtils.isEmpty(checkCode), "请输入手机验证码");
            String tmpCheckCode = SmsUtil.getMobileCheckCode(SmsUtil.CODE_KEY_FIND_PASSWORD_MOBILE, mobile);
            Assert.isTrue(!StringUtils.isEmpty(tmpCheckCode), "验证码已失效");// 比较输入的验证码和发送至手机端的验证码
            Assert.isTrue(tmpCheckCode.equals(checkCode), "验证码错误");
            String password = req.getParameter("password");
            Assert.isTrue(!StringUtils.isEmpty(password), "密码不能为空");
            ResultEntry<Admin> result = adminFacade.getByMobile(mobile);
            if (result.isSuccess() && result.getT() != null){
                Admin admin = new Admin();
                admin.setId(result.getT().getId());
                admin.setPassword(StringUtils.trimToNull(password));
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.FIND_LOGINPWD.getValue());
                adminBusiness.setExtend1("用户id:"+result.getT().getId());
                ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
                Result resultReset = adminFacade.updateAdmin(admin);
                if (resultReset.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                }else{
                    putError(model, resultReset.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                adminBusiness.setAdminId(result.getT().getId());
                adminBusiness.setCreateUserId(result.getT().getId());
                adminBusiness.setId(val.getT().getId());
                AdminTransactionUtil.updateAdminTransaction(adminBusiness);
            }else{
                putError(model, "该手机号码尚未注册");
            }
        }catch(Exception ex){
            logger.error("找回密码错误", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("resetPassWord出参:{}",model);
        responseAsJson(model, resp);
    }
    
    /**
     * 
     * 获取手机验证码(找回密码)
     * @param model
     * @param req
     * @param resp
     */
    @RequestMapping(value="/findPasswordSendcode.htm", method={RequestMethod.POST, RequestMethod.GET})
    public void findPasswordMobileCode(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String mobile = req.getParameter("mobile");
            Assert.isTrue(!StringUtils.isEmpty(mobile), "请输入手机号码");
            Assert.isTrue(VerifyUtil.isMobile(mobile), "请输入正确的手机号码");
            PointSymbol pointSymbol = new PointSymbol();
            pointSymbol.setCode(SmsUtil.getMobileCode(SmsUtil.CODE_KEY_FIND_PASSWORD_MOBILE, mobile, WebUtil.getUserIp(req), SmsUtil.LIFE_TIME_FIVE_MIN));// 验证码
            pointSymbol.setTemplateCode(MessageContextEnum.findPassword.getTemplateCode());
            pointSymbol.setMobile(mobile);
            SmsUtil.sendSMS(pointSymbol);
            putSuccess(model, "");
        } catch (Exception ex) {
            logger.error("获取手机验证码失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("MobileCode出参:{}", model);
        responseAsJson(model, resp);
    }
    
    /**
     * 更新个推clientId
     * 
     * @param model
     * @param form
     * @param req
     * @param resp
     */
    @RequestMapping(value="/user/updateClientId.htm", method={RequestMethod.POST, RequestMethod.GET})
    public void updateClientId(AdminCreateForm form, HttpServletRequest req, HttpServletResponse resp){
       Map<String, Object> model = new HashMap<String, Object>();
       try {
           Admin admin = new Admin();
           if(Util.isNotNull(form.getClientId())){
               admin.setClientId(form.getClientId());
               admin.setId(SpringSecurityUtil.getIntPrincipal());
               Result result = adminFacade.updateAdmin(admin);
               if (result.isSuccess()){
                   putSuccess(model, "");
               }else{
                   putError(model, result.getErrorMsg());
               }
           }else{
               putError(model, "clientId为空"); 
           }
       } catch (Exception ex) {
           logger.error("修改clientId失败", ex);
           putError(model, ex.getMessage());
       } 
       responseAsJson(model, resp);
    }
}
