package com.zjzmjr.admin.web.app.controller;

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

import com.zjzmjr.admin.web.app.form.AppReleaseForm;
import com.zjzmjr.core.api.app.IAppReleaseFacade;
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
import com.zjzmjr.core.model.app.AppRelease;
import com.zjzmjr.core.model.app.AppReleaseQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/appRelease")
public class AppReleaseController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AppReleaseController.class);

    private final static String index = "/WEB-INF/pages/app/appRelease.jsp";

    @Resource(name = "appReleaseFacade")
    private IAppReleaseFacade appReleaseFacade;
    
    @RequestMapping("/index.htm")
    public String getIndex(ModelMap model){
        //model.put("appReleaseAuth", SpringSecurityUtil.hasAuth(UserAuthParams.APP_RELEASE));
        model.put("appReleaseAuth", true);
        return index;
    }

    /**
     * app版本信息查询一览
     * 
     * @param resp
     * @param form
     */
//    @Security(auth = "APP_RELEASE", checkSource = true)
    @RequestMapping(value = "/user/getAppReleases.htm", method = RequestMethod.POST)
    public void getAppReleases(HttpServletResponse resp, AppReleaseForm form) {
        logger.debug("getAppReleases 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            AppReleaseQuery query = new AppReleaseQuery();
            query.setDevice(form.getDevice());
            query.setAppVersion(form.getAppVersion());
            if (StringUtils.isBlank(form.getCreateTime()) && StringUtils.isBlank(form.getLastTime())) {
                query.setCreateTime(null);
                query.setLastTime(null);
            } else {
                query.setCreateTime(DateUtil.parse(form.getCreateTime(), DateUtil.YY_MM_DD));
                query.setLastTime(DateUtil.parse(form.getLastTime(), DateUtil.YY_MM_DD));
            }
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultPage<AppRelease> result = appReleaseFacade.getAppReleases(query);
            logger.debug("getAppReleases 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("app版本信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 根据Id查询app版本信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/getAppReleaseById.htm", method = RequestMethod.POST)
    public void getAppReleaseById(HttpServletResponse resp, AppReleaseForm form) {
        logger.debug("getAppReleaseById 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            AppReleaseQuery query = new AppReleaseQuery();
            query.setId(form.getId());
            ResultEntry<AppRelease> result = appReleaseFacade.getAppReleaseById(query);
            logger.debug("getAppReleaseById 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("根据Id查询app版本信息出错：", ex);
            putError(model, ex.getMessage());
        }
    }

    /**
     * app发布
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/user/insertAppRelease.htm", method = RequestMethod.POST)
    public void insertAppRelease(HttpServletResponse resp, HttpServletRequest req, AppReleaseForm form) {
        logger.debug("insertAppRelease 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
        	AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.APP_RELEASE.getValue());
            adminBusiness.setExtend1("版本号："+form.getAppVersion());
            adminBusiness.setExtend2("下载地址："+form.getDownloadUrl());
            ResultEntry<AdminBusiness> val = AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            AppRelease record = new AppRelease();
            record.setAppVersion(form.getAppVersion());
            record.setDevice(form.getDevice());
            if(Util.isNotNull(form.getDownloadUrl()) 
                    && Util.isNotNull(form.getDownloadUrl().getOriginalFilename())){
                record.setDownloadUrl(FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_ADMIN).uploadApk(form.getDownloadUrl()));
            }
            record.setCreateTime(new Date());
            record.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = appReleaseFacade.insertAppRelease(record);
            logger.debug("insertAppRelease 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("错误消息："+result.getErrorMsg());
            }
            // 更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("app发布出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 修改app版本信息
     * 
     * @param resp
     * @param req
     * @param form
     */
   /* @RequestMapping(value = "/updateAppRelease.htm", method = RequestMethod.POST)
    public void updateAppRelease(HttpServletResponse resp, HttpServletRequest req, AppReleaseForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            AppRelease record = new AppRelease();
            record.setId(form.getId());
            record.setAppVersion(form.getAppVersion());
            record.setDevice(form.getDevice());
            if(Util.isNotNull(form.getDownloadUrl()) 
                    && Util.isNotNull(form.getDownloadUrl().getOriginalFilename())){
                record.setDownloadUrl(FileUploadUtil.getInstance().uploadApk(form.getDownloadUrl()));
            }
            record.setUpdateTime(new Date());
            record.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            record.setVersion(form.getVersion());
            ResultEntry<Integer> result = appReleaseFacade.updateAppRelease(record);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("修改app版本信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }*/

}
