/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.service.business.admin.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.menu.AuthTypeEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;
import com.zjzmjr.core.model.admin.AdminUserAuth;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.service.business.admin.AdminAuthService;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminAuthDao;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminDao;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminUserAuthDao;
/**
 * 
 * @author js
 * @version $Id: AuthServiceImpl.java, v 0.1 2015年10月29日 上午11:58:52 js Exp $
 */
@Service("adminAuthService")
public class AdminAuthServiceImpl implements AdminAuthService {
    private static final Logger logger = LoggerFactory.getLogger(AdminAuthServiceImpl.class);
    @Resource(name = "adminAuthDao")
    private AdminAuthDao adminAuthDao;
    
    @Resource(name = "adminUserAuthDao")
    private AdminUserAuthDao adminUserAuthDao;
    
    @Resource(name = "adminDao")
    private AdminDao adminDao;
    
    private boolean isSuperAdmin(Integer userId) {
        if (null == userId || userId <= 0) {
            return false;
        }
        Admin superAdmin = adminDao.getByUsername("admin");
        return (superAdmin != null && superAdmin.getId().equals(userId));
    }
    
    /**
     * @see com.yztz.finance.bussiness.service.console.AdminAuthService#queryByPage(com.yztz.finance.model.admin.AdminAuthQuery)
     */
    @Override
    public ResultPage<AdminAuth> queryByPage(AdminAuthQuery query) {
        logger.debug("queryByPage入参:{}",query);
        PageBean page = query.getPageBean();
        ResultPage<AdminAuth> result = new ResultPage<AdminAuth>();
        List<AdminAuth> list = adminAuthDao.queryByPage(query);
        int total = adminAuthDao.count(query);
        page.setTotalResults(total);
        result.setList(list);
        result.setPage(page);
        logger.debug("queryByPage出参:{}",result);
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.AdminAuthService#create(com.yztz.finance.model.admin.AdminAuth)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result create(AdminAuth adminAuth) {
        logger.debug("create人参:{}",adminAuth);
        adminAuthDao.create(adminAuth);
        return new Result();
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.AdminAuthService#delete(com.yztz.finance.model.admin.AdminAuth)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result delete(AdminAuth adminAuth) {
        logger.debug("delete人参:{}",adminAuth);
        adminAuthDao.delete(adminAuth.getId());
        return new Result();
    }

    /** 
     * @see com.yztz.finance.bussiness.service.console.AdminAuthService#update(com.yztz.finance.model.admin.AdminAuth)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result update(AdminAuth adminAuth) {
        logger.debug("update人参:{}",adminAuth);
        adminAuthDao.update(adminAuth);
        return new Result();
    }

    @Override
    public ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupUserAuth(Long userId) {
        logger.debug("getGroupUserAuth人参:{}",userId);
        ResultEntry<Map<String, List<AuthNodeDTO>>> result = new ResultEntry<Map<String, List<AuthNodeDTO>>>();
        if (Util.isNull(userId)){
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<AdminAuth> userAuths = null;
        if (isSuperAdmin(userId.intValue())) {//超级用户
            userAuths = adminAuthDao.getAuths(new AdminAuthQuery());
        } else {
        	userAuths = adminUserAuthDao.getAuthByUser(userId.intValue());
        }
        
        List<AdminAuth> auths = adminAuthDao.getAuths(new AdminAuthQuery());

        //将用户权限放入hash，快速查找
        Map<Integer, AdminAuth> map = new HashMap<Integer, AdminAuth>(1 + (int) (userAuths.size() * 1.5));
        for (AdminAuth ua : userAuths) {
            map.put(ua.getId(), ua);
        }
        //权限分组
        Map<String, List<AuthNodeDTO>> authMap = new LinkedHashMap<String, List<AuthNodeDTO>>();
        for (AuthTypeEnum tp : AuthTypeEnum.values()) {
            authMap.put(tp.getMessage(), new LinkedList<AuthNodeDTO>());
        }
 
        for (AdminAuth auth : auths) {
            AuthTypeEnum type = AuthTypeEnum.getByValue(auth.getType());
            if (type != null) {
                authMap.get(type.getMessage()).add(new AuthNodeDTO(auth.getId(), auth.getCode(), auth.getName(),
                        map.get(auth.getId()) != null));
            }
        }
        result.setT(authMap);
        logger.debug("getGroupUserAuth出参:{}",result);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result bindUserAuth(Integer userId, List<Integer> authIds) {
        logger.debug("bindUserAuth入参:{},{}",userId,authIds);
        Result result = new Result();
        if (Util.isNull(userId)){
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        //非超级管理员
        if (!isSuperAdmin(userId)) {
            List<AdminAuth> userAuths = adminUserAuthDao.getAuthByUser(userId.intValue());
            List<AdminAuth> bindAuths =
                    authIds.isEmpty() ? Collections.<AdminAuth>emptyList() : adminAuthDao.getByIds(authIds);

            Map<Integer, AdminAuth> userAuthMap = new HashMap<Integer, AdminAuth>(1 + (int) (userAuths.size() * 1.5));
            Map<Integer, AdminAuth> bindAuthMap = new HashMap<Integer, AdminAuth>(1 + (int) (bindAuths.size() * 1.5));

            for (AdminAuth auth : userAuths) {
                userAuthMap.put(auth.getId(), auth);
            }
            for (AdminAuth auth : bindAuths) {
                bindAuthMap.put(auth.getId(), auth);
            }

            List<Integer> del = new ArrayList<Integer>(userAuths.size());
            List<Integer> bind = new ArrayList<Integer>(bindAuths.size());

            for (AdminAuth auth : userAuths) {
                if (!bindAuthMap.containsKey(auth.getId())) {
                    del.add(auth.getId());
                }
            }
            for (AdminAuth auth : bindAuths) {
                if (!userAuthMap.containsKey(auth.getId())) {
                    bind.add(auth.getId());
                }
            }
            if (!bind.isEmpty()) {
                List<AdminUserAuth> uas = new ArrayList<AdminUserAuth>(bind.size());
                for (Integer id : bind) {
                    uas.add(new AdminUserAuth(userId, id));
                }
                adminUserAuthDao.batchInsert(uas);
            }
            if (!del.isEmpty()) {
                Map<String, Object> param = new HashMap<String, Object>(4);
                param.put("userId", userId);
                param.put("authIds", del);
                adminUserAuthDao.deleteUserMenuByAuthId(param);
            }
        }
        logger.debug("bindUserAuth出参:{}",result);
        return result;
    }
}
