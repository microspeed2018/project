package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.company.JobAuthority;

/**
 * 职位权限信息表
 * 
 * @author oms
 * @version $Id: JobAuthorityMapper.java, v 0.1 2017-8-29 上午11:18:24 oms Exp $
 */
public interface JobAuthorityMapper {

    /**
     * 根据角色id获取权限数据
     * 
     * @param jobId
     * @return
     */
    List<AdminAuth> getJobAuthByJobId(Integer jobId);

    /**
     * 批量插入
     *
     * @param jobAuths
     */
    public int insertJobAuthList(List<JobAuthority> jobAuths);
    
    /**
     * 删除角色权限
     * 
     * @param param
     * @return
     */
    public int deleteJobAuthByJobId(Map<String, Object> param);

    int insertJobAuthority(JobAuthority record);

    int updateJobAuthorityById(JobAuthority record);

}