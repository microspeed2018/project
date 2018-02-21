/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.console.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.zjzmjr.admin.web.console.form.MenuAddForm;
import com.zjzmjr.admin.web.console.form.MenuUpdateForm;
import com.zjzmjr.admin.web.home.controller.AdminLoggerController;
import com.zjzmjr.core.api.menu.IMenuFacade;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.menu.MenuProjectEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.menu.AdminMenu;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.MenuQuery;
import com.zjzmjr.core.model.menu.MenuUserBind;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * @author elliott
 * @version $Id: MenuController.java, v 0.1 2014-1-15 下午7:04:13 elliott Exp $
 */
@Controller
@RequestMapping("/console/menu.htm")
public class MenuController extends AdminLoggerController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    private final String menuView = "/WEB-INF/pages/console/menu/menu_oper.jsp";

    @Autowired
    private IMenuFacade menuFacade;

    /**
     * 菜单操作视图
     *
     * @param model
     * @return
     */
    @Security(auth = { "CONSOLE_MENU_ADD", "CONSOLE_MENU_UPDATE" }, view = "redirect:/unauthorized.htm")
    @RequestMapping(method = RequestMethod.GET)
    public String home(ModelMap model) {
        model.put("menuAddAuth", true);
        model.put("menuUpdateAuth", true);
        // model.put("menuAddAuth",
        // SpringSecurityUtil.hasAuth(ConsolePermissionEnum.CONSOLE_MENU_ADD.getValue())
        // || true);
        // model.put("menuUpdateAuth",
        // SpringSecurityUtil.hasAuth(ConsolePermissionEnum.CONSOLE_MENU_UPDATE.getValue())
        // || true);
        return menuView;
    }

    /**
     * 加载整棵菜单树
     *
     * @param resp
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = { "action=getMenuTree" })
    public void getMenuTree(HttpServletResponse resp) {
        logger.debug("getMenuTree 执行开始  入参:{}");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            // MenuNode root = this.adminMenuBizService.getUserMenuTree(null,
            // true);
            MenuQuery query = new MenuQuery();
            query.setContainsUnchecked(true);
            query.setProject(MenuProjectEnum.ADMIN.getValue());
            ResultEntry<MenuNode> result = menuFacade.getUserMenuTree(query);
            logger.debug("getMenuTree 执行结束  出参:{}", result);
            map.put("tree", result.getT());
            putSuccess(map, "成功");
        } catch (Exception e) {
            logger.error("查询菜单树出错", e);
            putError(map, "系统出错");
        }
        responseAsJson(map, resp);
    }

    /**
     * 添加菜单
     *
     * @param resp
     * @param form
     * @param bindResult
     */
    @Security(auth = "CONSOLE_MENU_ADD")
    @RequestMapping(method = { RequestMethod.POST }, params = { "action=addMenu" })
    public void addMenu(HttpServletResponse resp, MenuAddForm form, BindingResult bindResult,HttpServletRequest req) {
        logger.debug("addMenu 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        if (!resolveBindingError(form, bindResult, model)) {
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_INSERT_MENU.getValue());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            try {
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.setName(form.getName());
                adminMenu.setUrl(form.getUrl());
                adminMenu.setParent(form.getParent());
                adminMenu.setOrder(form.getOrder());
                adminMenu.setProject(MenuProjectEnum.ADMIN.getValue());
                Result result = menuFacade.addMenu(adminMenu);
                logger.debug("addMenu 执行结束  出参:{}", result);
                putSuccess(model, "添加成功");
                logAdmin("添加菜单,menu:{}", form);
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } catch (IllegalArgumentException e) {
                putError(model, e.getMessage());
            } catch (Exception e) {
                putError(model, "菜单添加失败");
                logger.error("菜单添加出错", e);
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("菜单添加失败");
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        }

        responseAsJson(model, resp);
    }

    /**
     * 修改菜单
     *
     * @param resp
     * @param form
     * @param bindResult
     */
    @Security(auth = "CONSOLE_MENU_UPDATE")
    @RequestMapping(method = RequestMethod.POST, params = { "action=updateMenu" })
    public void updateMenu(HttpServletResponse resp, MenuUpdateForm form, BindingResult bindResult,HttpServletRequest req) {
        logger.debug("updateMenu 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>(5);
        if (!resolveBindingError(form, bindResult, model)) {
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_MENU.getValue());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            try {
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.setId(form.getId());
                adminMenu.setName(form.getName());
                adminMenu.setUrl(form.getUrl());
                adminMenu.setOrder(form.getOrder());
                adminMenu.setProject(MenuProjectEnum.ADMIN.getValue());
                Result result = menuFacade.updateMenu(adminMenu);
                logger.debug("updateMenu 执行结束  出参:{}", result);
                putSuccess(model, "");
                logAdmin("修改菜单,form:{}", form);
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } catch (IllegalArgumentException e) {
                putError(model, e.getMessage());
            } catch (Exception e) {
                putError(model, "菜单更新出错");
                logger.error("菜单更新出错{}", form, e);
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("菜单更新出错");
            }
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        }

        responseAsJson(model, resp);
    }

    /**
     * 删除菜单
     *
     * @param id
     * @param resp
     */
    @Security(auth = "CONSOLE_MENU_UPDATE")
    @RequestMapping(method = RequestMethod.POST, params = { "action=deleteMenu" })
    public void deleteMenu(@RequestParam Integer id, HttpServletResponse resp,HttpServletRequest req) {
        logger.debug("deleteMenu 执行开始  入参:{}", id);
        Map<String, Object> model = new HashMap<String, Object>(5);
        //新增管理员事物
        AdminBusiness adminBusiness=new AdminBusiness();
        adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_DELETE_MENU.getValue());
        ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        try {
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.setId(id);
            adminMenu.setProject(MenuProjectEnum.ADMIN.getValue());
            menuFacade.deleteMenu(adminMenu);
            putSuccess(model, "");
            logAdmin("删除菜单，id:{}", id);
            adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
        } catch (IllegalArgumentException e) {
            putError(model, e.getMessage());
        } catch (Exception e) {
            putError(model, "菜单删除出错");
            logger.error("菜单删除出错{}", id, e);
            adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
            adminBusiness.setComment("菜单删除出错");
        }
        //更新管理员事物
        adminBusiness.setId(val.getT().getId());
        AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        logger.debug("deleteMenu 执行结束  出参:{}",model);
        responseAsJson(model, resp);
    }

    /**
     * 获得用户的菜单树
     */
    @Security(auth = "CONSOLE_ADMIN_MENU_GRANT")
    @RequestMapping(method = RequestMethod.POST, params = { "action=userMenuTree" })
    public void getUserMenuTree(@RequestParam Integer userId, HttpServletResponse resp) {
        logger.debug("getUserMenuTree 执行开始  入参:{}", userId);
        Map<String, Object> model = new HashMap<String, Object>(6);
        if (Util.isNotNull(userId)) {
            try {
                MenuQuery query = new MenuQuery();
                query.setContainsUnchecked(true);
                query.setUserId(userId);
                query.setProject(MenuProjectEnum.ADMIN.getValue());
                ResultEntry<MenuNode> result = menuFacade.getUserMenuTree(query);
                logger.debug("getUserMenuTree 执行结束  出参:{}", result);
                model.put("userMenu", result.getT());
                putSuccess(model, "");
            } catch (Exception e) {
                putError(model, "加载用户权限树出错");
                logger.error("加载用户权限树出错,userId:{}", SpringSecurityUtil.getPrincipal(), e);
            }
        } else {
            putError(model, "用户ID不能为空");
        }
        responseAsJson(model, resp);
    }

    /**
     * 绑定用户菜单
     *
     * @param menus
     * @param userId
     * @param resp
     */
    @Security(auth = "CONSOLE_ADMIN_MENU_GRANT")
    @RequestMapping(method = RequestMethod.POST, params = { "action=bindMenu" })
    public void bindUserMenu(@RequestParam String menus, @RequestParam Integer userId, HttpServletResponse resp,HttpServletRequest req) {
        logger.debug("bindUserMenu 执行开始  入参:{}{}", menus,userId);
        Map<String, Object> model = new HashMap<String, Object>();
        if (Util.isNotNull(userId)) {
            String[] strs = StringUtils.trimToEmpty(menus).split(",");
            List<Integer> menuIds = new ArrayList<Integer>(strs.length);
            for (String str : strs) {
                try {
                    menuIds.add(Integer.parseInt(str, 10));
                } catch (NumberFormatException e) {
                }
            }
            MenuUserBind menuUserBind = new MenuUserBind();
            menuUserBind.setMenuIds(menuIds);
            menuUserBind.setUserId(userId);
            menuUserBind.setProject(MenuProjectEnum.ADMIN.getValue());
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_BIND_USER_MENU.getValue());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            Result result = menuFacade.bindUserMenu(menuUserBind);
            logger.debug("bindUserMenu 执行结束  出参:{}", result);
            putSuccess(model, "");
            //更新管理员事物
            adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
            logAdmin("绑定用户菜单,userId:{},memus:{}", userId, menus);
        } else {
            putError(model, "userId不存在");
        }
        responseAsJson(model, resp);
    }

}
