package com.zjzmjr.core.api.company;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.model.company.JobAuthority;

/**
 * 职位权限服务接口
 * 
 * @author oms
 * @version $Id: IJobAuthFacade.java, v 0.1 2017-8-29 下午2:48:36 oms Exp $
 */
public interface IJobAuthFacade {

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
