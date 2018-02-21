package com.zjzmjr.admin.web.console.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.admin.web.console.form.RoleMenuForm;
import com.zjzmjr.core.api.menu.IRoleMenuFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.RoleMenuBind;
import com.zjzmjr.core.model.menu.RoleMenuQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 角色菜单控制层
 * 
 * @author oms
 * @version $Id: RoleMenuController.java, v 0.1 2017-2-15 上午9:52:20 oms Exp $
 */
@Controller
@RequestMapping(value="/console/roleMenu.htm")
public class RoleMenuController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(RoleMenuController.class);

    private final String menuView = "/WEB-INF/pages/console/menu/role_menu.jsp";

    @Resource(name = "roleMenuFacade")
    private IRoleMenuFacade roleMenuFacade;
    
    @RequestMapping(method = RequestMethod.GET)
    public String home(ModelMap model){
       // model.put("roleTypeEnums", AdminRoleTypeEnum.values());
        return menuView;
    }
    
    /**
     * 
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"action=queryByPage"})
    public void queryByPage(HttpServletResponse resp, RoleMenuForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
//            RoleMenuQuery query = new RoleMenuQuery();
//            query.setId(form.getId());
//            query.setRoleType(form.getRoleType());
//            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
//            query.setPageBean(pageBean);
//            ResultPage<RoleMenu> result = roleMenuFacade.getRoleMenuByCondition(query);
//            if(result.isSuccess()){
//                model.put("total", result.getPage().getTotalResults());
//                model.put("rows", result.getList());
//                putSuccess(model, "");
//            } else {
//                model.put("total", 0);
//                model.put("rows", Collections.emptyList());
//                putError(model, result.getErrorCode(), result.getErrorMsg());
//            }
 //         model.put("total", AdminRoleTypeEnum.values().length);
 //         model.put("rows", AdminRoleTypeEnum.values());
          putSuccess(model, "");
        } catch (Exception e) {
            logger.error("查询角色出错", e);
            putError(model, "查询角色出错");
        }
        responseAsJson(model, resp);
    }

    /**
     * 角色菜单树
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, params = {"action=roleMenuTree"})
    public void roleMenuTree(HttpServletResponse resp, RoleMenuForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            RoleMenuQuery query = new RoleMenuQuery();
            query.setRoleType(form.getRoleType());
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultEntry<MenuNode> result = roleMenuFacade.getRoleMenuTree(query);
            if(result.isSuccess()){
                model.put("userMenu", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("查询菜单树出错", e);
            putError(model, "查询菜单树出错");
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
    @RequestMapping(method = RequestMethod.POST, params = { "action=bindMenu" })
    public void bindUserMenu(@RequestParam String menus, @RequestParam Integer roleType, HttpServletResponse resp, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        if (Util.isNotNull(roleType)) {
            String departmentId = req.getParameter("departmentId");
            String[] strs = StringUtils.trimToEmpty(menus).split(",");
            List<Integer> menuIds = new ArrayList<Integer>(strs.length);
            for (String str : strs) {
                try {
                    menuIds.add(Integer.parseInt(str, 10));
                } catch (NumberFormatException e) {
                }
            }
            RoleMenuBind menuUserBind = new RoleMenuBind();
            menuUserBind.setCompanyId(SpringSecurityUtil.getIntCompany());
            menuUserBind.setMenuIds(menuIds);
            menuUserBind.setRoleType(roleType);
            menuUserBind.setDepartmentId(StringUtil.nullToInteger(departmentId));
            menuUserBind.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            Result result = roleMenuFacade.bindRoleMenu(menuUserBind);
            if(result.isSuccess()){
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }

            logger.info("绑定用户菜单,roleType:{},memus:{}", roleType, menus);
        } else {
            putError(model, "roleType不存在");
        }
        responseAsJson(model, resp);
    }

}
