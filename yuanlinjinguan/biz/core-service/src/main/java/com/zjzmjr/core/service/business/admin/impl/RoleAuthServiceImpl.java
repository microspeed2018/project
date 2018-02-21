package com.zjzmjr.core.service.business.admin.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.menu.AuthTypeEnum;
import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;
import com.zjzmjr.core.model.admin.AdminUserAuth;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.model.admin.RoleAuth;
import com.zjzmjr.core.service.business.admin.RoleAuthService;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminAuthDao;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminUserAuthDao;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.RoleAuthDao;

/**
 * 角色权限接口实现
 * 
 * @author UI
 * @version $Id: RoleAuthServiceImpl.java, v 0.1 2017-6-7 下午2:40:39 UI Exp $
 */
@Service("roleAuthService")
public class RoleAuthServiceImpl implements RoleAuthService {
    private static final Logger logger = LoggerFactory.getLogger(RoleAuthServiceImpl.class);
    @Resource(name = "adminAuthDao")
    private AdminAuthDao adminAuthDao;
    
    @Resource(name = "adminUserAuthDao")
    private AdminUserAuthDao adminUserAuthDao;
    
    @Resource(name = "roleAuthDao")
    private RoleAuthDao roleAuthDao;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.RoleAuthService#getGroupRoleAuth(java.lang.Long)
     */
    @Override
    public ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupRoleAuth(Long roleId) {
        logger.debug("getGroupRoleAuth人参:{}",roleId);
        ResultEntry<Map<String, List<AuthNodeDTO>>> result = new ResultEntry<Map<String, List<AuthNodeDTO>>>();
        if (Util.isNull(roleId)){
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<AdminAuth> roleAuths = null;
        roleAuths = roleAuthDao.getRoleAuthByRoleId(roleId.intValue());
        List<AdminAuth> auths = adminAuthDao.getAuths(new AdminAuthQuery());
        //将用户权限放入hash，快速查找
        Map<Integer, AdminAuth> map = new HashMap<Integer, AdminAuth>(1 + (int) (roleAuths.size() * 1.5));
        for (AdminAuth ua : roleAuths) {
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
        logger.debug("getGroupRoleAuth出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.RoleAuthService#bindRoleAuth(java.lang.Integer, java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result bindRoleAuth(Integer roleId, List<Integer> authIds, Integer currUserId) {
        logger.debug("bindRoleAuth入参:{} roleId{}",roleId+" authIds:{}"+authIds);
        Result result = new Result();
        if (Util.isNull(roleId)){
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<AdminAuth> roleAuths = roleAuthDao.getRoleAuthByRoleId(roleId.intValue());
        List<AdminAuth> bindAuths = authIds.isEmpty() ? Collections.<AdminAuth>emptyList() : adminAuthDao.getByIds(authIds);
        Map<Integer, AdminAuth> roleAuthMap = new HashMap<Integer, AdminAuth>(1 + (int) (roleAuths.size() * 1.5));
        Map<Integer, AdminAuth> bindAuthMap = new HashMap<Integer, AdminAuth>(1 + (int) (bindAuths.size() * 1.5));
        for (AdminAuth auth : roleAuths) {
            roleAuthMap.put(auth.getId(), auth);
        }
        for (AdminAuth auth : bindAuths) {
            bindAuthMap.put(auth.getId(), auth);
        }
        List<Integer> del = new ArrayList<Integer>(roleAuths.size());
        List<Integer> bind = new ArrayList<Integer>(bindAuths.size());
        for (AdminAuth auth : roleAuths) {
            if (!bindAuthMap.containsKey(auth.getId())) {
                del.add(auth.getId());
            }
        }
        for (AdminAuth auth : bindAuths) {
            if (!roleAuthMap.containsKey(auth.getId())) {
                bind.add(auth.getId());
            }
        }
        //保存当前角色的权限设置
        if (!bind.isEmpty()) {
            List<RoleAuth> uas = new ArrayList<RoleAuth>(bind.size());
            for (Integer id : bind) {
                uas.add(new RoleAuth(roleId, id, new Date(), currUserId));
            }
            logger.info("修改角色权限参数：{}", uas);
            int authInsertCount = roleAuthDao.insertRoleAuthList(uas);
            if (authInsertCount > 0) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
                result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + "修改角色权限失败");
            }
        }
        if (!del.isEmpty()) {
            Map<String, Object> param = new HashMap<String, Object>(4);
            param.put("roleId", roleId);
            param.put("authIds", del);
            logger.info("删除角色权限参数：{}", param);
            int authDeleteCount = roleAuthDao.deleteRoleAuthByRoleId(param);
            if (authDeleteCount > 0) {
                result.setSuccess(true);
            } else {
                try {
                    throw new ApplicationException("删除角色权限失败");
                } catch (ApplicationException e) {
                    logger.error("删除角色权限失败", e);
                }
                result.setSuccess(false);
                result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
                result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + "修改角色权限失败");
            }
        }
        //更新含当前角色用户的权限数据
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("roleType", roleId);
        paramMap.put("departmentId", 1);
        List<Integer> userIdList = adminUserAuthDao.getAuthUserIdByRoleId(paramMap);
        if (Util.isNotNull(userIdList)) {
            List<AdminUserAuth> authList = new ArrayList<>();
            for (Integer userId : userIdList) {
                for (Integer bindAuthId : authIds) {
                    AdminUserAuth auth = new AdminUserAuth();
                    auth.setUserId(userId);
                    auth.setAuthId(bindAuthId);
                    auth.setTime(new Date());
                    authList.add(auth);
                }
            }
            //首先删除所有当前角色用户的权限数据
            logger.info("删除角色权限参数：{}", paramMap);
            adminUserAuthDao.deleteAuthByRoleId(paramMap);
            if (Util.isNotNull(authList) && authList.size() > 0) {
                logger.info("绑定角色权限参数：{}", authList);
                int authInsertCount = adminUserAuthDao.batchInsert(authList);//重新添加用户的权限数据
                if (authInsertCount > 0) {
                    result.setSuccess(true);
                } else {
                    try {
                        throw new ApplicationException("添加角色权限失败");
                    } catch (ApplicationException e) {
                        logger.error("添加角色权限失败", e);
                    }
                    result.setSuccess(false);
                    result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
                    result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
                    logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + "添加角色权限失败");
                }
            }
        }
        logger.debug("bindRoleAuth出参:{}",result);
        return result;
    }
}
