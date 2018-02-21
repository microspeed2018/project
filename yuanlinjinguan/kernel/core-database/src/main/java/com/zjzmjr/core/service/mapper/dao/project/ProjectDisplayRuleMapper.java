package com.zjzmjr.core.service.mapper.dao.project;

import com.zjzmjr.core.model.project.ProjectDisplayRule;

/**
 * 项目一览展示条件表
 * 
 * @author oms
 * @version $Id: ProjectDisplayRuleMapper.java, v 0.1 2017-9-4 下午4:54:54 oms Exp $
 */
public interface ProjectDisplayRuleMapper {

    /**
     * 插入项目一览展示条件表
     * 
     * @param record
     * @return
     */
    int insertProjectDisplayRule(ProjectDisplayRule record);

    /**
     * 根据用户编号查询项目一览展示条件表
     * 
     * @param userId
     * @return
     */
    ProjectDisplayRule getProjectDisplayRuleByUserId(Integer userId);

    /**
     * 根据主键编号更新项目一览展示条件表
     * 
     * @param record
     * @return
     */
    int updateProjectDisplayRuleById(ProjectDisplayRule record);

}