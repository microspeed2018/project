/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.provider.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.admin.IAdminAuthFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.service.business.admin.AdminAuthService;

/**
 * 
 * @author js
 * @version $Id: AuthFacadeImpl.java, v 0.1 2015年10月29日 上午11:55:30 js Exp $
 */
@Service("adminAuthFacade")
public class AdminAuthFacadeImpl implements IAdminAuthFacade {

    private static final Logger logger = LoggerFactory.getLogger(AdminAuthFacadeImpl.class);

    @Resource(name = "adminAuthService")
    private AdminAuthService adminAuthService;

    /**
     * @see com.yztz.finance.api.admin.IAdminAuthFacade#queryByPage(com.yztz.finance.model.admin.AdminAuthQuery)
     */
    @Override
    public ResultPage<AdminAuth> queryByPage(AdminAuthQuery query) {
        ResultPage<AdminAuth> result = new ResultPage<AdminAuth>();
        try{
            result = adminAuthService.queryByPage(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * @see com.yztz.finance.api.admin.IAdminAuthFacade#create(com.yztz.finance.model.admin.AdminAuth)
     */
    @Override
    public Result create(AdminAuth adminAuth) {
        Result result = new Result();
        try{
            result = adminAuthService.create(adminAuth);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * @see com.yztz.finance.api.admin.IAdminAuthFacade#delete(com.yztz.finance.model.admin.AdminAuth)
     */
    @Override
    public Result delete(AdminAuth adminAuth) {
        Result result = new Result();
        try{
            result = adminAuthService.delete(adminAuth);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * @see com.yztz.finance.api.admin.IAdminAuthFacade#update(com.yztz.finance.model.admin.AdminAuth)
     */
    @Override
    public Result update(AdminAuth adminAuth) {
        Result result = new Result();
        try{
            result = adminAuthService.update(adminAuth);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.admin.IAdminAuthFacade#getGroupUserAuth(java.lang.Long)
     */
    @Override
    public ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupUserAuth(Long userId) {
        ResultEntry<Map<String, List<AuthNodeDTO>>> result = new ResultEntry<Map<String,List<AuthNodeDTO>>>();
        try{
            result = adminAuthService.getGroupUserAuth(userId);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.admin.IAdminAuthFacade#bindUserAuth(java.lang.Integer, java.util.List)
     */
    @Override
    public Result bindUserAuth(Integer userId, List<Integer> authIds) {
        Result result = new Result();
        try{
            result = adminAuthService.bindUserAuth(userId, authIds);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

}
