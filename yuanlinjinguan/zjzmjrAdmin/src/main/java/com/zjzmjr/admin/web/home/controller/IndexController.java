/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.home.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.api.menu.IMenuFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.enums.menu.MenuProjectEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.MenuQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 登录后首页
 *
 * @author elliott
 * @version $Id: IndexController.java, v 0.1 2014-1-14 下午2:44:04 elliott Exp $
 */
@Controller
@RequestMapping(value = "/home/index.htm")
public class IndexController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final String indexView = "/WEB-INF/pages/home/index.jsp";
    
    private final static String upcoming = "/upcoming/index.htm";
    
    private final static String welcome = "/welcome.html";

    @Resource(name = "menuFacade")
    private IMenuFacade menuFacade;

    @Resource(name = "adminFacade")
    private IAdminFacade adminFacade;
    
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model) {
        MenuQuery query = new MenuQuery();
        query.setUserId(SpringSecurityUtil.getIntPrincipal());
        query.setProject(MenuProjectEnum.ADMIN.getValue());
        ResultRecord<MenuNode> result = menuFacade.getTopMenu(query); // SpringSecurityUtil.getIntPrincipal());
        model.put("userMenus", result.getList());
        // model.put("fundOutExplainEnums",
        // FundInOutExplainEnum.getOutExplains());
        // model.put("fundInExplainEnums",
        // FundInOutExplainEnum.getInExplains());
        ResultEntry<Admin> results = adminFacade.getById(SpringSecurityUtil.getIntPrincipal());
        String index = upcoming;
        if(results.isSuccess()){
            if(GenerateConstants.number_1.equals(results.getT().getLoginSucceed())){
               index = welcome;
            }
        }
        model.put("res_url", index);
        return indexView;
    }

    @RequestMapping(method = RequestMethod.POST, params = { "action=getSecondaryMenu" })
    public void getSecondaryMenu(@RequestParam("parent") Integer parent, HttpServletResponse resp) {
        logger.debug("getSecondaryMenu 执行开始  入参:{}", parent);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            MenuQuery query = new MenuQuery();
            query.setUserId(SpringSecurityUtil.getIntPrincipal());
            query.setContainsUnchecked(false);
            query.setProject(MenuProjectEnum.ADMIN.getValue());
            ResultEntry<MenuNode> result = menuFacade.getUserMenuTree(query);
            List<MenuNode> sedMenus = result.getT().getChildren();
            List<MenuNode> menus = null;
            if (sedMenus != null && !sedMenus.isEmpty()) {
                for (MenuNode node : sedMenus) {
                    if (node.getId().equals(parent)) {
                        menus = node.getChildren();
                    }
                }
            }
            logger.debug("getSecondaryMenu 执行结束  出参:{}", menus);
            model.put("menus", menus == null ? Collections.emptyList() : menus);
            putSuccess(model, "");
        } catch (Exception e) {
            putError(model, "菜单加载失败");
            logger.error("菜单加载出错", e);
        }
        responseAsJson(model, resp);
    }

}
