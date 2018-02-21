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

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminQuery;
import com.zjzmjr.core.model.menu.AdminMenu;
import com.zjzmjr.core.model.menu.AdminUserMenu;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.MenuUserBind;
import com.zjzmjr.core.model.menu.RoleMenu;
import com.zjzmjr.core.model.menu.RoleMenuBind;
import com.zjzmjr.core.model.menu.RoleMenuQuery;
import com.zjzmjr.core.service.business.menu.RoleMenuService;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminDao;
import com.zjzmjr.core.service.mapper.dao.coredb.menu.AdminMenuDao;
import com.zjzmjr.core.service.mapper.dao.coredb.menu.AdminUserMenuDao;
import com.zjzmjr.core.service.mapper.dao.coredb.menu.RoleMenuMapper;

/**
 * 角色菜单业务层
 * 
 * @author oms
 * @version $Id: RoleMenuServiceImpl.java, v 0.1 2017-2-14 下午1:18:34 oms Exp $
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {

    private static final Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

    @Resource(name = "roleMenuMapper")
    private RoleMenuMapper roleMenuMapper;

    @Resource(name = "adminMenuDao")
    private AdminMenuDao adminMenuDao;

    @Resource(name = "adminUserMenuDao")
    private AdminUserMenuDao adminUserMenuDao;

    @Resource(name = "adminDao")
    private AdminDao adminDao;

    /**
     * 
     * @see com.zjzmjr.core.service.business.menu.RoleMenuService#getRoleMenuByCondition(com.zjzmjr.core.model.menu.RoleMenuQuery)
     */
    @Override
    public ResultPage<RoleMenu> getRoleMenuByCondition(RoleMenuQuery query) {
        logger.debug("getRoleMenuByCondition入参:{}",query);
        ResultPage<RoleMenu> result = new ResultPage<RoleMenu>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), query.toString());
            logger.debug("getRoleMenuByCondition出参:{}",result);
            return result;
        }

        int total = roleMenuMapper.getRoleMenuCount(query);
        if (total > 0) {
            List<RoleMenu> roleList = roleMenuMapper.getRoleMenuByCondition(query);
            result.setList(roleList);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getRoleMenuByCondition出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.menu.RoleMenuService#bindRoleMenu(com.zjzmjr.core.model.menu.RoleMenuBind)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result bindRoleMenu(RoleMenuBind roleMenuBind) {
        logger.debug("bindRoleMenu入参:{}",roleMenuBind);
        Result result = new Result();
        Integer roleType = roleMenuBind.getRoleType();
        List<Integer> menuIds = roleMenuBind.getMenuIds();

        List<AdminMenu> roleMenu = roleMenuMapper.getRoleMenuByRoleType(roleType);
        List<AdminMenu> bindMenu = menuIds.isEmpty() ? Collections.<AdminMenu> emptyList() : adminMenuDao.getByIds(menuIds);

        Map<Integer, Object> userMap = new HashMap<Integer, Object>((int) (roleMenu.size() * 1.5));
        Map<Integer, Object> bindMap = new HashMap<Integer, Object>((int) (bindMenu.size() * 1.5));

        for (AdminMenu am : roleMenu) {
            userMap.put(am.getId(), am);
        }
        for (AdminMenu bm : bindMenu) {
            bindMap.put(bm.getId(), bm);
        }

        List<Integer> del = new ArrayList<Integer>(roleMenu.size());
        List<Integer> bind = new ArrayList<Integer>(bindMenu.size());

        for (AdminMenu am : roleMenu) {
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
            delUserMenuParam.put("roleType", roleType);
            delUserMenuParam.put("menuIds", del);
            roleMenuMapper.deleteRoleMenuByMenuIds(delUserMenuParam);
        }
        if (!bind.isEmpty()) {
            List<RoleMenu> ums = new ArrayList<RoleMenu>(bind.size());
            for (Integer mid : bind) {
                ums.add(new RoleMenu(roleMenuBind.getCompanyId(), roleType, mid, new Date(), roleMenuBind.getCreateUserId()));
            }
            roleMenuMapper.batchInsertRoleMenu(ums);
        }
        result.setSuccess(true);

        AdminQuery adminQuery = new AdminQuery();
        adminQuery.setJobId(roleType);
        adminQuery.setDepartmentId(roleMenuBind.getDepartmentId());
        adminQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<Integer> adminRst = queryByPage(adminQuery);
        if (adminRst.isSuccess()) {
            List<Integer> user = new ArrayList<Integer>(adminRst.getList().size());
            List<AdminUserMenu> ums = new ArrayList<AdminUserMenu>(bind.size());
            Map<String, Object> delUserMenuParam = null;
            for (Integer userId : adminRst.getList()) {
                user.add(userId);
                // 用户解绑菜单
//                if (!del.isEmpty()) {
                    delUserMenuParam = new HashMap<String, Object>();
                    delUserMenuParam.put("userId", userId);
//                    delUserMenuParam.put("menuIds", del);
                    delUserMenuParam.put("cleanUserMenu", 1);
                    adminUserMenuDao.deleteUserMenuByMenuId(delUserMenuParam);
//                }
                
                // 用户绑定菜单
                if (!menuIds.isEmpty()) {
                    for (Integer mid : menuIds) {
                        ums.add(new AdminUserMenu(userId, mid));
                    }
                }
            }
            // 用户绑定菜单
            if (!ums.isEmpty()) {
                adminUserMenuDao.batchInsert(ums);
            }
        }
        logger.debug("bindRoleMenu出参:{}",result);
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.MenuService#getAdminMenuTree(com.yztz.finance.model.admin.MenuQuery)
     */
    @Override
    public ResultEntry<MenuNode> getRoleMenuTree(RoleMenuQuery query) {
        logger.debug("getRoleMenuTree入参:{}",query);
        ResultEntry<MenuNode> result = new ResultEntry<MenuNode>();

        MenuNode root = new MenuNode(0, "菜单", "", 0, false);
        List<AdminMenu> menus = adminMenuDao.getMenu(null);

        // 构建树
        buildTree(menus, root);

        List<AdminMenu> userMenus = roleMenuMapper.getRoleMenuByRoleType(query.getRoleType());
        Map<Integer, AdminMenu> menuMap = new HashMap<Integer, AdminMenu>(1 + (int) (userMenus.size() * 1.5));
        for (AdminMenu um : userMenus) {
            menuMap.put(um.getId(), um);
        }
        recurseMenuNode(root, menuMap);

        result.setT(root);
        result.setSuccess(true);
        logger.debug("getRoleMenuTree出参:{}",result);
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.menu.RoleMenuService#bindUserMenu(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> bindUserMenu(Integer roleType, Integer userId) {
        logger.debug("bindUserMenu入参:roleType:{},userId:{}",roleType,userId);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        result.setSuccess(false);
        List<AdminMenu> admenuLst = roleMenuMapper.getRoleMenuByRoleType(roleType);
        if (admenuLst != null && !admenuLst.isEmpty()) {
            List<AdminUserMenu> ums = new ArrayList<AdminUserMenu>(admenuLst.size());
            for (AdminMenu menu : admenuLst) {
                ums.add(new AdminUserMenu(userId, menu.getId()));
            }
            adminUserMenuDao.batchInsert(ums);
            result.setSuccess(true);
        }
        logger.debug("bindUserMenu出参:{}",result);
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.menu.RoleMenuService#bindUpdateUserMenu(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> bindUpdateUserMenu(Integer roleType, Integer userId) {
        logger.debug("bindUpdateUserMenu入参:roleType:{},userId:{}",roleType,userId);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        List<AdminMenu> admenuLst = Collections.emptyList();
        List<Integer> menuIds = new ArrayList<Integer>();
        if (roleType != null) {
            admenuLst = roleMenuMapper.getRoleMenuByRoleType(roleType);
            for (AdminMenu menu : admenuLst) {
                menuIds.add(menu.getId());
            }
        }

        MenuUserBind menuUserBind = new MenuUserBind();
        menuUserBind.setUserId(userId);
        menuUserBind.setMenuIds(menuIds);
        bindUserMenu(menuUserBind);
        result.setSuccess(true);
        logger.debug("bindUpdateUserMenu出参:{}",result);
        return result;
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
        return root;
    }

    private MenuNode convertMenu(AdminMenu menu) {
        MenuNode node = new MenuNode();
        node.setId(menu.getId());
        node.setText(menu.getName());
        node.setUrl(StringUtils.trimToEmpty(menu.getUrl()));
        node.setOrder(menu.getOrder());
        node.setProject(menu.getProject());
        return node;
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


    private ResultPage<Integer> queryByPage(AdminQuery adminQuery) {
        logger.debug("queryByPage入参:{}", adminQuery);
//        PageBean page = adminQuery.getPageBean();
        ResultPage<Integer> result = new ResultPage<Integer>();
//        List<Admin> admins = adminDao.queryByPage(adminQuery);
//        int total = adminDao.count(adminQuery);
//        page.setTotalResults(total);
//        result.setList(admins);
//        result.setPage(page);
        
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("roleType", adminQuery.getJobId());
        param.put("departmentId", adminQuery.getDepartmentId());
        List<Integer> list = roleMenuMapper.getMenuUserIdByRoleId(param);
        if(list == null || list.isEmpty()){
            result.setSuccess(false);
        }else{
            result.setSuccess(true);
            result.setList(list);
        }
        
        logger.debug("queryByPage出参:{}", result);
        return result;
    }

    /**
     * 绑定用户菜单
     * 
     * @param menuUserBind
     * @return
     */
    private Result bindUserMenu(MenuUserBind menuUserBind) {
        logger.debug("bindUserMenu入参:{}",menuUserBind);
        Result result = new Result();
        Integer userId = menuUserBind.getUserId();
        List<Integer> menuIds = menuUserBind.getMenuIds();
        Admin user = adminDao.getById(menuUserBind.getUserId());
        Assert.notNull(user, "用户不存在");

        // 非超级管理员
        if (!isSuperAdmin(userId)) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", userId);
            param.put("parentId", null);
            List<AdminMenu> userMenu = adminUserMenuDao.getMenuByUser(param);
            List<AdminMenu> bindMenu = menuIds.isEmpty() ? Collections.<AdminMenu> emptyList() : adminMenuDao.getByIds(menuIds);

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

    private boolean isSuperAdmin(Integer userId){
        Admin superAdmin = adminDao.getByUsername("admin");
        return (superAdmin != null && superAdmin.getId().equals(userId));
    }
    
}
