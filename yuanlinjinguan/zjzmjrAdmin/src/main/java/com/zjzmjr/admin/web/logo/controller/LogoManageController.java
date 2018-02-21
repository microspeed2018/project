package com.zjzmjr.admin.web.logo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.admin.web.logo.form.LogoManageForm;
import com.zjzmjr.core.api.logo.ILogoManageFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.common.util.FileUploadUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.logo.LogoManage;
import com.zjzmjr.core.model.logo.LogoManageQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 后台图标管理控制层
 * 
 * @author lenovo
 * @version $Id: LogoManageController.java, v 0.1 2016-9-20 下午5:00:45 lenovo Exp $
 */
@Controller
@RequestMapping("/logo")
public class LogoManageController extends BaseController{
    
    private static final Logger logger = LoggerFactory.getLogger(LogoManageController.class);

    private final static String index = "/WEB-INF/pages/logo/logoManage.jsp";
    
    @Resource(name = "logoManageFacade")
    private ILogoManageFacade logoManageFacade;
    
    @RequestMapping(value="/index.htm" )
    public String index(ModelMap model) {
        return index;
    }
    
    /**
     * 图标管理一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/getLogoManage.htm", method = RequestMethod.POST)
    public void getLogoManage(HttpServletResponse resp,LogoManageForm form){
        logger.debug("getLogoManage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            LogoManageQuery query = new LogoManageQuery();
            query.setId(form.getId());
            query.setLogoTypeNo(form.getLogoTypeNo());
            query.setLogoComment(form.getLogoComment());
            if (StringUtils.isBlank(form.getCreateTime()) && StringUtils.isBlank(form.getLastTime())) {
                query.setCreateTime(null);
                query.setLastTime(null);
            } else {
                query.setCreateTime(DateUtil.parse(form.getCreateTime(), DateUtil.YY_MM_DD));
                query.setLastTime(DateUtil.parse(form.getLastTime(), DateUtil.YY_MM_DD));
            }
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultPage<LogoManage> result = logoManageFacade.getLogoManage(query);
            logger.debug("getLogoManage 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("图标管理一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 图标管理修改
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/updateLogoManage.htm", method = RequestMethod.POST)
    public void updateLogoManage(HttpServletResponse resp,LogoManageForm form,HttpServletRequest req){
        logger.debug("updateLogoManage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            LogoManage logoManage = new LogoManage();
            logoManage.setId(form.getId());
            logoManage.setLogoNo(form.getLogoNo());
            logoManage.setLogoTypeNo(form.getLogoTypeNo());
            logoManage.setLogoComment(form.getLogoComment());
            if (Util.isNotNull(form.getLogoAddress()) && Util.isNotNull(form.getLogoAddress().getOriginalFilename())) {
                logoManage.setLogoAddress(FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_ADMIN).uploadApk(form.getLogoAddress()));
            }
            logoManage.setLogoLinkAddress(form.getLogoLinkAddress());
            logoManage.setUpdateTime(new Date());
            logoManage.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_LOGO.getValue());
            String message=StringUtils.isEmpty(form.getLogoComment())?"没有图标说明":"\t图标说明："+form.getLogoComment();
            adminBusiness.setExtend1("图标id："+form.getId()+message);
            message=StringUtils.isEmpty(form.getLogoLinkAddress())?"没有链接地址":"链接地址："+form.getLogoLinkAddress();
            adminBusiness.setExtend2(message);
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = logoManageFacade.updateLogoManage(logoManage);
            logger.debug("updateLogoManage 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("错误消息："+result.getErrorMsg());
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("图标管理修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 图标新增
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/insertLogoManage.htm", method = RequestMethod.POST)
    public void insertLogoManage(HttpServletResponse resp,LogoManageForm form,HttpServletRequest req){
        logger.debug("insertLogoManage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            LogoManage logoManage = new LogoManage();
            logoManage.setLogoTypeNo(form.getLogoTypeNo());
            ResultEntry<Integer> resultNo=logoManageFacade.getMaxLogoNo(form.getLogoTypeNo());
            logoManage.setLogoNo(resultNo.getT());
            logoManage.setLogoComment(form.getLogoComment());
            if (Util.isNotNull(form.getLogoAddress()) && Util.isNotNull(form.getLogoAddress().getOriginalFilename())) {
                logoManage.setLogoAddress(FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_ADMIN).uploadApk(form.getLogoAddress()));
            }
            logoManage.setLogoLinkAddress(form.getLogoLinkAddress());
            logoManage.setCreateTime(new Date());
            logoManage.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_LOGO.getValue());
            String message=StringUtils.isEmpty(form.getLogoComment())?"没有图标说明":"图标说明："+form.getLogoComment();
            adminBusiness.setExtend1(message);
            message=StringUtils.isEmpty(form.getLogoLinkAddress())?"没有链接地址":"链接地址："+form.getLogoLinkAddress();
            adminBusiness.setExtend2(message);
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = logoManageFacade.insertLogoManage(logoManage);
            logger.debug("insertLogoManage 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("错误消息："+result.getErrorMsg());
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("图标管理新增出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    } 
    
    /**
     * 删除图标
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/user/deleteLogoManage.htm", method = RequestMethod.POST)
    public void deleteLogoManage(HttpServletResponse resp,LogoManageForm form,HttpServletRequest req){
        logger.debug("deleteLogoManage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_DELETE_LOGO.getValue());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result=logoManageFacade.deleteLogoManage(form.getId());
            adminBusiness.setExtend1("图标id："+form.getId());
            logger.debug("deleteLogoManage 执行结束  出参:{}", result);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("错误消息："+result.getErrorMsg());
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("图标管理删除出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
