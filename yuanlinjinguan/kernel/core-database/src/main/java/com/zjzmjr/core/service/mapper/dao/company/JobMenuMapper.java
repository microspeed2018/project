package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.model.company.JobMenu;
import com.zjzmjr.core.model.company.JobMenuQuery;
import com.zjzmjr.core.model.menu.AdminMenu;

/**
 * 职位菜单信息表
 * 
 * @author oms
 * @version $Id: JobMenuMapper.java, v 0.1 2017-8-29 下午4:44:57 oms Exp $
 */
public interface JobMenuMapper {
    
    List<AdminMenu> getJobMenuByJobId(Integer jobId);
    
    int getJobMenuCount(JobMenuQuery query);
    
    List<JobMenu> getJobMenuByCondition(JobMenuQuery query);

    int deleteJobMenuByMenuIds(Map<String, Object> param);

    /**
     * 批量插入角色的菜单
     * 
     * @param userMenus
     */
    int batchInsertJobMenu(List<JobMenu> jobMenus);

    int insertJobMenu(JobMenu record);

    int updateJobMenuById(JobMenu record);

}