package com.zjzmjr.core.service.business.company.impl;

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
import com.zjzmjr.core.model.company.JobMenu;
import com.zjzmjr.core.model.company.JobMenuBind;
import com.zjzmjr.core.model.company.JobMenuQuery;
import com.zjzmjr.core.model.menu.AdminMenu;
import com.zjzmjr.core.model.menu.AdminUserMenu;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.MenuUserBind;
import com.zjzmjr.core.service.business.company.JobMenuService;
import com.zjzmjr.core.service.mapper.dao.company.JobMenuMapper;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminDao;
import com.zjzmjr.core.service.mapper.dao.coredb.menu.AdminMenuDao;
import com.zjzmjr.core.service.mapper.dao.coredb.menu.AdminUserMenuDao;

/**
 * 职位菜单业务处理类
 * 
 * @author oms
 * @version $Id: JobMenuServiceImpl.java, v 0.1 2017-8-29 下午4:54:05 oms Exp $
 */
@Service("jobMenuService")
public class JobMenuServiceImpl implements JobMenuService {

    private static final Logger logger = LoggerFactory.getLogger(JobMenuServiceImpl.class);

    @Resource(name = "adminMenuDao")
    private AdminMenuDao adminMenuDao;

    @Resource(name = "adminUserMenuDao")
    private AdminUserMenuDao adminUserMenuDao;

    @Resource(name = "adminDao")
    private AdminDao adminDao;
    
    @Resource(name = "jobMenuMapper")
    private JobMenuMapper jobMenuMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.JobMenuService#getJobMenuByCondition(com.zjzmjr.core.model.company.JobMenuQuery)
     */
    @Override
    public ResultPage<JobMenu> getJobMenuByCondition(JobMenuQuery query) {
        ResultPage<JobMenu> result = new ResultPage<JobMenu>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), query);
            return result;
        }

        int total = jobMenuMapper.getJobMenuCount(query);
        if (total > 0) {
            List<JobMenu> roleList = jobMenuMapper.getJobMenuByCondition(query);
            result.setList(roleList);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.JobMenuService#bindJobMenu(com.zjzmjr.core.model.company.JobMenuBind)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result bindJobMenu(JobMenuBind jobMenuBind) {
        Result result = new Result();
        Integer jobId = jobMenuBind.getJobId();
        List<Integer> menuIds = jobMenuBind.getMenuIds();

        List<AdminMenu> jobMenu = jobMenuMapper.getJobMenuByJobId(jobId);
        List<AdminMenu> bindMenu = menuIds.isEmpty() ? Collections.<AdminMenu> emptyList() : adminMenuDao.getByIds(menuIds);

        Map<Integer, Object> userMap = new HashMap<Integer, Object>((int) (jobMenu.size() * 1.5));
        Map<Integer, Object> bindMap = new HashMap<Integer, Object>((int) (bindMenu.size() * 1.5));

        for (AdminMenu am : jobMenu) {
            userMap.put(am.getId(), am);
        }
        for (AdminMenu bm : bindMenu) {
            bindMap.put(bm.getId(), bm);
        }

        List<Integer> del = new ArrayList<Integer>(jobMenu.size());
        List<Integer> bind = new ArrayList<Integer>(bindMenu.size());

        for (AdminMenu am : jobMenu) {
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
            delUserMenuParam.put("jobId", jobId);
            delUserMenuParam.put("menuIds", del);
            jobMenuMapper.deleteJobMenuByMenuIds(delUserMenuParam);
        }
        if (!bind.isEmpty()) {
            List<JobMenu> ums = new ArrayList<JobMenu>(bind.size());
            for (Integer mid : bind) {
                ums.add(new JobMenu(jobMenuBind.getCompanyId(), jobId, mid, new Date(), jobMenuBind.getCreateUserId()));
            }
            jobMenuMapper.batchInsertJobMenu(ums);
        }
        result.setSuccess(true);

        AdminQuery adminQuery = new AdminQuery();
        adminQuery.setJobId(jobId);
        adminQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<Admin> adminRst = queryByPage(adminQuery);
        if (adminRst != null && !adminRst.getList().isEmpty()) {
            List<Integer> user = new ArrayList<Integer>(adminRst.getList().size());
            List<AdminUserMenu> ums = new ArrayList<AdminUserMenu>(bind.size());
            Map<String, Object> delUserMenuParam = null;
            for (Admin admin : adminRst.getList()) {
                user.add(admin.getId());
                // 用户解绑菜单
                //if (!del.isEmpty()) {
                    delUserMenuParam = new HashMap<String, Object>();
                    delUserMenuParam.put("userId", admin.getId());
                   // delUserMenuParam.put("menuIds", del);
                    delUserMenuParam.put("cleanUserMenu", 1);
                    adminUserMenuDao.deleteUserMenuByMenuId(delUserMenuParam);
                //}
                
                // 用户绑定菜单
                if (!menuIds.isEmpty()) {
                    for (Integer mid : menuIds) {
                        ums.add(new AdminUserMenu(admin.getId(), mid));
                    }
                }
            }
            // 用户绑定菜单
            if (!ums.isEmpty()) {
                adminUserMenuDao.batchInsert(ums);
            }
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.JobMenuService#getJobMenuTree(com.zjzmjr.core.model.company.JobMenuQuery)
     */
    @Override
    public ResultEntry<MenuNode> getJobMenuTree(JobMenuQuery query) {
        ResultEntry<MenuNode> result = new ResultEntry<MenuNode>();

        MenuNode root = new MenuNode(0, "菜单", "", 0, false);
        List<AdminMenu> menus = adminMenuDao.getMenu(null);

        // 构建树
        buildTree(menus, root);

        List<AdminMenu> userMenus = jobMenuMapper.getJobMenuByJobId(query.getJobId());
        Map<Integer, AdminMenu> menuMap = new HashMap<Integer, AdminMenu>(1 + (int) (userMenus.size() * 1.5));
        for (AdminMenu um : userMenus) {
            menuMap.put(um.getId(), um);
        }
        recurseMenuNode(root, menuMap);

        result.setT(root);
        result.setSuccess(true);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> bindUserMenu(Integer jobId, Integer userId) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        result.setSuccess(false);
        List<AdminMenu> admenuLst = jobMenuMapper.getJobMenuByJobId(jobId);
        if (admenuLst != null && !admenuLst.isEmpty()) {
            List<AdminUserMenu> ums = new ArrayList<AdminUserMenu>(admenuLst.size());
            for (AdminMenu menu : admenuLst) {
                ums.add(new AdminUserMenu(userId, menu.getId()));
            }
            adminUserMenuDao.batchInsert(ums);
            result.setSuccess(true);
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> bindUpdateUserMenu(Integer jobId, Integer userId) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        List<AdminMenu> admenuLst = Collections.emptyList();
        List<Integer> menuIds = new ArrayList<Integer>();
        if (jobId != null) {
            admenuLst = jobMenuMapper.getJobMenuByJobId(jobId);
            for (AdminMenu menu : admenuLst) {
                menuIds.add(menu.getId());
            }
        }

        MenuUserBind menuUserBind = new MenuUserBind();
        menuUserBind.setUserId(userId);
        menuUserBind.setMenuIds(menuIds);
        bindUserMenu(menuUserBind);
        result.setSuccess(true);
        return result;
    }

    private ResultPage<Admin> queryByPage(AdminQuery adminQuery) {
        PageBean page = adminQuery.getPageBean();
        ResultPage<Admin> result = new ResultPage<Admin>();
        List<Admin> admins = adminDao.queryByPage(adminQuery);
        int total = adminDao.count(adminQuery);
        page.setTotalResults(total);
        result.setList(admins);
        result.setPage(page);
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

    /**
     * 绑定用户菜单
     * 
     * @param menuUserBind
     * @return
     */
    private Result bindUserMenu(MenuUserBind menuUserBind) {
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
        return result;
    }

    private boolean isSuperAdmin(Integer userId){
        Admin superAdmin = adminDao.getByUsername("admin");
        return (superAdmin != null && superAdmin.getId().equals(userId));
    }
    
}
