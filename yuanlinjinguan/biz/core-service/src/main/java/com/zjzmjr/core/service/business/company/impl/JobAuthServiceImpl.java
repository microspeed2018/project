package com.zjzmjr.core.service.business.company.impl;

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
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.menu.AuthTypeEnum;
import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;
import com.zjzmjr.core.model.admin.AdminUserAuth;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.model.company.JobAuthority;
import com.zjzmjr.core.service.business.company.JobAuthService;
import com.zjzmjr.core.service.mapper.dao.company.JobAuthorityMapper;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminAuthDao;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminUserAuthDao;

/**
 * 职位权限业务处理层
 * 
 * @author oms
 * @version $Id: JobAuthServiceImpl.java, v 0.1 2017-8-29 下午2:05:23 oms Exp $
 */
@Service("jobAuthService")
public class JobAuthServiceImpl implements JobAuthService {

    private static final Logger logger = LoggerFactory.getLogger(JobAuthServiceImpl.class);

    @Resource(name = "adminAuthDao")
    private AdminAuthDao adminAuthDao;

    @Resource(name = "adminUserAuthDao")
    private AdminUserAuthDao adminUserAuthDao;

    @Resource(name = "jobAuthorityMapper")
    private JobAuthorityMapper jobAuthorityMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.JobAuthService#getGroupJobAuth(java.lang.Long)
     */
    @Override
    public ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupJobAuth(Long jobId) {
        ResultEntry<Map<String, List<AuthNodeDTO>>> result = new ResultEntry<Map<String, List<AuthNodeDTO>>>();
        if (Util.isNull(jobId)){
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<AdminAuth> roleAuths = null;
        roleAuths = jobAuthorityMapper.getJobAuthByJobId(jobId.intValue());
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
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.JobAuthService#bindJobAuth(java.lang.Integer,
     *      java.util.List, java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> bindJobAuth(JobAuthority jobAuth, List<Integer> authIds) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(jobAuth.getJobId())){
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<AdminAuth> roleAuths = jobAuthorityMapper.getJobAuthByJobId(jobAuth.getJobId().intValue());
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
            List<JobAuthority> uas = new ArrayList<JobAuthority>(bind.size());
            for (Integer id : bind) {
                uas.add(new JobAuthority(jobAuth.getCompanyId(), jobAuth.getJobId(), id, new Date(), jobAuth.getCreateUserId()));
            }
            logger.info("修改角色权限参数：{}", uas);
            int authInsertCount = jobAuthorityMapper.insertJobAuthList(uas);
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
            param.put("jobId", jobAuth.getJobId());
            param.put("authIds", del);
            logger.info("删除角色权限参数：{}", param);
            int authDeleteCount = jobAuthorityMapper.deleteJobAuthByJobId(param);
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
        paramMap.put("roleType", jobAuth.getJobId());
        paramMap.put("departmentId", jobAuth.getDepartmentId());
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
        return result;
    }

}
