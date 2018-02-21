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

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.admin.web.console.form.AuthAddForm;
import com.zjzmjr.admin.web.console.form.AuthQueryForm;
import com.zjzmjr.admin.web.home.controller.AdminLoggerController;
import com.zjzmjr.common.util.CollectionUtil;
import com.zjzmjr.core.api.admin.IAdminAuthFacade;
import com.zjzmjr.core.api.company.IJobAuthFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.menu.AuthTypeEnum;
import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.model.company.JobAuthority;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * @author elliott
 * @version $Id: AuthController.java, v 0.1 2014-1-17 下午2:08:22 elliott Exp $
 */
@Controller
@RequestMapping("/console/auth.htm")
public class AuthController extends AdminLoggerController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final String queryView = "/WEB-INF/pages/console/auth/auth_query.jsp";

    @Autowired
    private IAdminAuthFacade adminAuthFacade;
    
    @Resource(name = "jobAuthFacade")
    private IJobAuthFacade jobAuthFacade;

    /**
     * 分页查询视图
     *
     * @return
     */
    @Security(auth = "CONSOLE_AUTH_QUERY", view = "redirect:/unauthorized.htm")
    @RequestMapping(method = RequestMethod.GET)
    public String queryByPageView(ModelMap model) {
        model.put("typeEnums", AuthTypeEnum.values());
        model.put("authUpdateAuth", true);
        model.put("authAddAuth", true);
        // model.put("authUpdateAuth",
        // SpringSecurityUtil.hasAuth(ConsolePermissionEnum.CONSOLE_AUTH_UPDATE.getValue()));
        // model.put("authAddAuth",
        // SpringSecurityUtil.hasAuth(ConsolePermissionEnum.CONSOLE_AUTH_ADD.getValue()));
        return queryView;
    }

    /**
     * 分页查询权限
     *
     * @param rows
     * @param page
     * @param resp
     * @param form
     * @param bindResult
     */
    @SuppressWarnings("unchecked")
    @Security(auth = "CONSOLE_AUTH_QUERY")
    @RequestMapping(method = RequestMethod.POST, params = "action=queryByPage")
    public void queryByPage(@RequestParam(required = false) Integer rows, @RequestParam(required = false) Integer page, HttpServletResponse resp, AuthQueryForm form, BindingResult bindResult) {
        logger.debug("queryByPage 执行开始  入参:{}{}{}", form,rows,page);
        JSONObject model = new JSONObject(); 
        if (!resolveBindingError(form, bindResult, model)) {
            try {
                ResultPage<AdminAuth> result = adminAuthFacade.queryByPage(convertQuery(form, page, rows));
                logger.debug("queryByPage 执行结束  出参:{}", result);
                JsonConfig config = new JsonConfig();
                config.setExcludes(new String[] { "time", "version" });
                model.put("total", result.getPage().getTotalResults());
                model.accumulate("rows", result.getList(), config);
                putSuccess(model, "");
            } catch (IllegalArgumentException e) {
                putError(model, e.getMessage());
            } catch (Exception e) {
                putError(model, "查询出错");
                logger.error("权限查询出错{}", form, e);
            }
        }
        responseAsJson(resp, model);
    }

    private AdminAuthQuery convertQuery(AuthQueryForm form, int page, int rows) {
        PageBean pageBean = new PageBean(rows, page);
        AdminAuthQuery query = new AdminAuthQuery();
        query.setCode(StringUtils.trimToNull(form.getAuthCode()));
        query.setName(StringUtils.trimToNull(form.getAuthName()));
        if (form.getType() != null) {
            query.setType(CollectionUtil.asList(form.getType()));
        }
        query.setPageBean(pageBean);
        return query;
    }

    /**
     * 添加权限
     *
     * @param form
     * @param bindResult
     */
    @Security(auth = "CONSOLE_AUTH_ADD", checkSource = true)
    @RequestMapping(method = RequestMethod.POST, params = "action=addAuth")
    public void addAuth(HttpServletResponse resp, AuthAddForm form, BindingResult bindResult,HttpServletRequest req) {
        logger.debug("addAuth 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        if (!resolveBindingError(form, bindResult, model)) {
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_AUTH.getValue());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            try {
                AdminAuth adminAuth = new AdminAuth();
                adminAuth.setCode(StringUtils.trimToEmpty(form.getAuthCode()));
                adminAuth.setName(StringUtils.trimToEmpty(form.getAuthName()));
                adminAuth.setType(form.getType());
                adminAuth.setTime(new Date());
                Result result = adminAuthFacade.create(adminAuth);
                logger.debug("addAuth 执行结束  出参:{}", result);
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());

                logAdmin("添加权限,auth:{}", form);
            } catch (IllegalArgumentException e) {
                putError(model, e.getMessage());
            } catch (Exception e) {
                putError(model, "权限添加出错");
                logger.error("添加权限错误", e);
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("权限添加出错");
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        }
        responseAsJson(model, resp);
    }

    /**
     * 获取用户权限的邦定情况
     *
     * @param userId
     * @param resp
     */
    @Security(auth = "CONSOLE_ADMIN_AUTH_GRANT")
    @RequestMapping(method = RequestMethod.POST, params = { "action=userGroupAuth" })
    public void getUserGroupAuth(@RequestParam Long userId, HttpServletResponse resp) {
        logger.debug("getUserGroupAuth 执行开始  入参:{}", userId);
        Map<String, Object> model = new HashMap<String, Object>(6);
        if (userId != null) {
            try {
                 Map<String, List<AuthNodeDTO>> list = adminAuthFacade.getGroupUserAuth(userId).getT();
                 logger.debug("getUserGroupAuth 执行结束  出参:{}", list);
                 model.put("authGroup", list);
                putSuccess(model, "");
            } catch (Exception e) {
                putError(model, "用户权限查询出错");
                logger.error("用户权限查询出错,userId:{}", userId, e);
            }
        } else {
            putError(model, "用户id不能为空");
        }
        responseAsJson(model, resp);
    }

    /**
     * 绑定用户权限
     *
     * @param auths
     * @param userId
     * @param resp
     */
    // @Security(auth = "CONSOLE_ADMIN_AUTH_GRANT", checkSource =
    // SystemConstants.checkSource)
    @RequestMapping(method = RequestMethod.POST, params = "action=bindAuth")
    public void bindUserAuth(@RequestParam String auths, @RequestParam Integer userId, HttpServletResponse resp,HttpServletRequest req) {
        logger.debug("bindUserAuth 执行开始  入参:{}{}", auths,userId);
        Map<String, Object> model = new HashMap<String, Object>(6);
        if (userId != null) {
            String[] strs = StringUtils.trimToEmpty(auths).split(",");
            List<Integer> authIds = new ArrayList<Integer>(strs.length);
            for (String str : strs) {
                try {
                    authIds.add(Integer.parseInt(str, 10));
                } catch (NumberFormatException e) {
                }
            }
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_BIND_USER_AUTH.getValue());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            try {
                adminAuthFacade.bindUserAuth(userId, authIds);
                // adminAuthBizService.bindUserAuth(userId, authIds);
                putSuccess(model, "");
                logAdmin("绑定用户权限,userId:{},auths:{}", userId, auths);
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } catch (Exception e) {
                putError(model, "绑定出错");
                logger.error("绑定用户权限出错,userId:{},auths:{}", userId, auths, e);
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("绑定出错");
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } else {
            putError(model, "用户ID不能为空");
        }
        logger.debug("bindUserAuth 执行结束  出参:{}",model);
        responseAsJson(model, resp);
    }

    /**
     * 删除权限
     *
     * @param id
     * @param resp
     */
    // @Security(auth = "CONSOLE_AUTH_UPDATE", checkSource =
    // SystemConstants.checkSource)
    @RequestMapping(method = RequestMethod.POST)
    public void deleteAuth(@RequestParam Integer id, HttpServletResponse resp,HttpServletRequest req) {
        logger.debug("deleteAuth 执行开始  入参:{}", id);
        Map<String, Object> model = new HashMap<String, Object>(6);
        //新增管理员事物
        AdminBusiness adminBusiness=new AdminBusiness();
        adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_DELETE_AUTH.getValue());
        ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        try {
            AdminAuth adminAuth = new AdminAuth();
            adminAuth.setId(id);
            adminAuthFacade.delete(adminAuth);
            putSuccess(model, "");
            logAdmin("删除权限,authId:{}", id);
            adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
        } catch (IllegalArgumentException e) {
            putError(model, e.getMessage());
        } catch (Exception e) {
            putError(model, "权限删除出错出错");
            logger.error("权限删除出错出错,id:{}", id, e);
            adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
            adminBusiness.setComment("权限删除出错出错");
        }
        //更新管理员事物
        adminBusiness.setId(val.getT().getId());
        AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        logger.debug("deleteAuth 执行结束  出参:{}", model);
        responseAsJson(model, resp);
    }

    /**
     * 获取职位权限的邦定情况
     *
     * @param userId
     * @param resp
     */
    @Security(auth = "CONSOLE_ADMIN_AUTH_GRANT")
    @RequestMapping(method = RequestMethod.POST, params = { "action=JobGroupAuth" })
    public void getJobGroupAuth(@RequestParam Long id, HttpServletResponse resp) {
        Map<String, Object> model = new HashMap<String, Object>(6);
        if (id != null) {
            try {
                 Map<String, List<AuthNodeDTO>> list = jobAuthFacade.getGroupJobAuth(id).getT();
                 model.put("authGroup", list);
                putSuccess(model, "");
            } catch (Exception e) {
                putError(model, "用户权限查询出错");
                logger.error("用户权限查询出错,jobId:{}", id, e);
            }
        } else {
            putError(model, "职位id不能为空");
        }
        responseAsJson(model, resp);
    }

    /**
     * 绑定职位权限
     *
     * @param auths
     * @param userId
     * @param resp
     */
    // @Security(auth = "CONSOLE_ADMIN_AUTH_GRANT", checkSource =
    // SystemConstants.checkSource)
    @RequestMapping(method = RequestMethod.POST, params = "action=bindJobAuth")
    public void bindJobAuth(@RequestParam String auths, @RequestParam Integer jobId, HttpServletResponse resp,HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>(6);
        if (jobId != null) {
            String departmentId = req.getParameter("departmentId");
            String[] strs = StringUtils.trimToEmpty(auths).split(",");
            List<Integer> authIds = new ArrayList<Integer>(strs.length);
            for (String str : strs) {
                try {
                    authIds.add(Integer.parseInt(str, 10));
                } catch (NumberFormatException e) {
                }
            }
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_BIND_USER_AUTH.getValue());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            try {
                JobAuthority jobAuth = new JobAuthority(SpringSecurityUtil.getIntCompany(), jobId, null, null, SpringSecurityUtil.getIntPrincipal());
                jobAuth.setDepartmentId(StringUtil.nullToInteger(departmentId));
                jobAuthFacade.bindJobAuth(jobAuth, authIds);
                putSuccess(model, "");
                logAdmin("绑定用户权限,jobId:{},auths:{}", jobId, auths);
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } catch (Exception e) {
                putError(model, "绑定出错");
                logger.error("绑定用户权限出错,jobId:{},auths:{}", jobId, auths, e);
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("绑定出错");
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } else {
            putError(model, "职位ID不能为空");
        }
        responseAsJson(model, resp);
    }

}
