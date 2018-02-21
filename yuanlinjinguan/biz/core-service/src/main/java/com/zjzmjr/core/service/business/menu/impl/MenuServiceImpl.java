/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.service.business.menu.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.menu.AdminMenu;
import com.zjzmjr.core.model.menu.AdminUserMenu;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.MenuQuery;
import com.zjzmjr.core.model.menu.MenuUserBind;
import com.zjzmjr.core.service.business.admin.AdminService;
import com.zjzmjr.core.service.business.menu.MenuService;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminDao;
import com.zjzmjr.core.service.mapper.dao.coredb.menu.AdminMenuDao;
import com.zjzmjr.core.service.mapper.dao.coredb.menu.AdminUserMenuDao;

/**
 * 
 * @author js
 * @version $Id: MenuServiceImpl.java, v 0.1 2015年10月29日 下午3:36:41 js Exp $
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Resource(name = "adminMenuDao")
    private AdminMenuDao adminMenuDao;

    @Resource(name = "adminUserMenuDao")
    private AdminUserMenuDao adminUserMenuDao;

    @Resource(name = "adminService")
    private AdminService adminService;

    @Resource(name = "adminDao")
    private AdminDao adminDao;
    
    /**
     * @see com.yztz.finance.bussiness.service.console.MenuService#getAdminMenuTree(com.yztz.finance.model.admin.MenuQuery)
     */
    @Override
    public ResultEntry<MenuNode> getUserMenuTree(MenuQuery query) {
        logger.debug("getUserMenuTree入参:{}",query);
        ResultEntry<MenuNode> result = new ResultEntry<MenuNode>();
        Integer userId = query.getUserId();
        boolean containsUnchecked = query.getContainsUnchecked();
        boolean isSuper = adminService.isSuperAdmin(userId);

        Map<String, Object> param = new HashMap<String, Object>(4);
        param.put("userId", userId);
        param.put("parent", null);
        MenuNode root = new MenuNode(0, "菜单", "", 0, false);
        List<AdminMenu> menus = null;
        menus = (userId == null || userId <= 0 || isSuper || containsUnchecked) ? adminMenuDao.getMenu(null) : adminUserMenuDao.getMenuByUser(param);
        
        // 构建树
        buildTree(menus, root);
        if (userId == null || userId <= 0 || isSuper) {
            recurseMenuNodeChecked(root);
        } else if (containsUnchecked) {
            List<AdminMenu> userMenus = null;
            userMenus = adminUserMenuDao.getMenuByUser(param);
            Map<Integer, AdminMenu> menuMap = new HashMap<Integer, AdminMenu>(1 + (int) (userMenus.size() * 1.5));
            for (AdminMenu um : userMenus) {
                menuMap.put(um.getId(), um);
            }
            recurseMenuNode(root, menuMap);
        }
        logger.debug("getUserMenuTree出参:{}",result);
        result.setT(root);
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.MenuService#getTopMenu(com.yztz.finance.model.admin.MenuQuery)
     */
    @Override
    public ResultRecord<MenuNode> getTopMenu(MenuQuery query) {
        logger.debug("getTopMenu入参:{}",query);
        List<AdminMenu> mnus = Collections.emptyList();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentId", 0);
        if (adminService.isSuperAdmin(query.getUserId())) {
            mnus = adminMenuDao.getMenu(map);
        } else {
            map.put("userId", query.getUserId());
            mnus = adminUserMenuDao.getMenuByUser(map);
        }
        List<MenuNode> nodes = new ArrayList<MenuNode>(mnus.size());
        for (AdminMenu menu : mnus) {
            nodes.add(convertMenu(menu));
        }
        ResultRecord<MenuNode> result = new ResultRecord<MenuNode>();
        result.setList(nodes);
        logger.debug("getTopMenu出参:{}",result);
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.MenuService#addMenu(com.yztz.finance.model.admin.AdminMenu)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result addMenu(AdminMenu adminMenu) {
        logger.debug("addMenu入参:{}",adminMenu);
        Result result = new Result();
        adminMenu.setTime(new Date());
        adminMenu.setOrder(adminMenu.getOrder() == null || adminMenu.getOrder() < 0 ? 0 : adminMenu.getOrder());

        adminMenuDao.addMenu(adminMenu);
        logger.debug("addMenu出参:{}",result);
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.MenuService#updateMenu(com.yztz.finance.model.admin.AdminMenu)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result updateMenu(AdminMenu adminMenu) {
        logger.debug("updateMenu入参:{}",adminMenu);
        Result result = new Result();

        AdminMenu menu = adminMenuDao.getById(adminMenu.getId());
        AdminMenu mod = new AdminMenu(menu.getId());
        mod.setName(StringUtils.trimToEmpty(adminMenu.getName()));
        mod.setUrl(StringUtils.trimToEmpty(adminMenu.getUrl()));
        mod.setOrder(adminMenu.getOrder() == null ? 0 : adminMenu.getOrder());
        mod.setProject(adminMenu.getProject());
        adminMenuDao.update(mod);
        logger.debug("updateMenu出参:{}",result);
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.MenuService#deleteMenu(com.yztz.finance.model.admin.AdminMenu)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result deleteMenu(AdminMenu adminMenu) {
        logger.debug("deleteMenu入参:{}",adminMenu);
        Result result = new Result();
        Integer id = adminMenu.getId();
        AdminMenu menu = null;

        menu = adminMenuDao.getById(id);
        Assert.notNull(menu, "菜单不存在");
        List<Integer> ids = getChildren(id, adminMenu.getProject());
        ids.add(id);

        adminMenuDao.deleteByIds(ids);
        adminUserMenuDao.deleteByMenuIds(ids);
        logger.debug("deleteMenu出参:{}",result);
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.MenuService#bindUserMenu(com.yztz.finance.model.admin.MenuUserBind)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result bindUserMenu(MenuUserBind menuUserBind) {
        logger.debug("bindUserMenu入参:{}",menuUserBind);
        Result result = new Result();
        Integer userId = menuUserBind.getUserId();
        List<Integer> menuIds = menuUserBind.getMenuIds();
        Admin user = adminDao.getById(menuUserBind.getUserId());
        Assert.notNull(user, "用户不存在");
        // 非超级管理员
        if (!adminService.isSuperAdmin(userId)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", userId);
            param.put("parentId", null);
            List<AdminMenu> userMenu = null;
            List<AdminMenu> bindMenu = null;

            userMenu = adminUserMenuDao.getMenuByUser(param);
            bindMenu = menuIds.isEmpty() ? Collections.<AdminMenu> emptyList() : adminMenuDao.getByIds(menuIds);
            Map<Integer, Object> userMap = new HashMap<Integer, Object>((int) (userMenu.size() * 1.5));
            Map<Integer, Object> bindMap = new HashMap<Integer, Object>((int) (bindMenu.size() * 1.5));
            for (AdminMenu am : userMenu) {
                userMap.put(am.getId(), am);
            }
            for (AdminMenu bm : bindMenu) {
                bindMap.put(bm.getId(), bm);
            }

            List<Integer> del = new ArrayList<Integer>(userMenu.size());
            List<Integer> bind = new ArrayList<Integer>(bindMenu.size());

            for (AdminMenu am : userMenu) {
                if (!bindMap.containsKey(am.getId())) {
                    del.add(am.getId());
                }
            }
            for (AdminMenu bm : bindMenu) {
                if (!userMap.containsKey(bm.getId())) {
                    bind.add(bm.getId());
                }
            }


            if (!del.isEmpty()) {
                Map<String, Object> delUserMenuParam = new HashMap<String, Object>();
                delUserMenuParam.put("userId", userId);
                delUserMenuParam.put("menuIds", del);
                adminUserMenuDao.deleteUserMenuByMenuId(delUserMenuParam);
            }
            if (!bind.isEmpty()) {
                List<AdminUserMenu> ums = new ArrayList<AdminUserMenu>(bind.size());
                for (Integer mid : bind) {
                    ums.add(new AdminUserMenu(userId, mid));
                }
                adminUserMenuDao.batchInsert(ums);
            }
        }
        logger.debug("bindUserMenu出参:{}",result);
        return result;
    }

    private MenuNode convertMenu(AdminMenu menu) {
        logger.debug("convertMenu入参:{}",menu);
        MenuNode node = new MenuNode();
        node.setId(menu.getId());
        node.setText(menu.getName());
        node.setUrl(StringUtils.trimToEmpty(menu.getUrl()));
        node.setOrder(menu.getOrder());
        node.setProject(menu.getProject());
        return node;
    }

    private MenuNode buildTree(List<AdminMenu> menus, MenuNode root) {
        Map<Integer, MenuNode> nodes = new HashMap<Integer, MenuNode>(menus.size() * 2);
        nodes.put(root.getId(), root);
        for (AdminMenu mnu : menus) {
            MenuNode node = convertMenu(mnu);
            MenuNode parent = nodes.get(mnu.getParent());
            if (parent != null) {
                parent.addChild(node);
                nodes.put(node.getId(), node);
            }
        }
        logger.debug("convertMenu出参:{}",root);
        return root;
    }

    private void recurseMenuNodeChecked(MenuNode root) {
        if (root != null) {
            root.setChecked(true);
            if (root.getChildren() != null && !root.getChildren().isEmpty()) {
                for (MenuNode dto : root.getChildren()) {
                    recurseMenuNodeChecked(dto);
                }
            }
        }
    }

    private void recurseMenuNode(MenuNode root, Map<Integer, AdminMenu> menus) {
        if (root != null) {
            if (root.getChildren() != null && !root.getChildren().isEmpty()) {
                for (MenuNode dto : root.getChildren()) {
                    recurseMenuNode(dto, menus);
                }
            } else if (menus.containsKey(root.getId())) {
                root.setChecked(true);
            }
        }
    }

    private List<Integer> getChildren(Integer parentId, Integer project) {
        logger.debug("getChildren入参:parentId:{},project:{}",parentId,project);
        List<Integer> ids = new ArrayList<Integer>();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parentId", parentId);
        List<AdminMenu> menus = null;

        menus = adminMenuDao.getMenu(param);
        if (!menus.isEmpty()) {
            for (AdminMenu menu : menus) {
                ids.add(menu.getId());
                ids.addAll(getChildren(menu.getId(), project));
            }
        }
        logger.debug("getChildren出参:{}",ids);
        return ids;
    }

}
