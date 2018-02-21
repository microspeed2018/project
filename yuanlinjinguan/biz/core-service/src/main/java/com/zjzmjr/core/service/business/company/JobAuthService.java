package com.zjzmjr.core.service.business.company;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.model.company.JobAuthority;

/**
 * 职位权限业务处理层
 * 
 * @author oms
 * @version $Id: JobAuthService.java, v 0.1 2017-8-29 下午2:03:33 oms Exp $
 */
public interface JobAuthService {

    /**
     * 获取角色权限
     * 
     * @param roleId
     * @return
     */
    ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupJobAuth(Long jobId);
    
    /**
     * 绑定角色权限
     * 
     * @param userId
     * @param authIds
     * @return
     */
    ResultEntry<Integer> bindJobAuth(JobAuthority jobAuth, List<Integer> authIds);

}
