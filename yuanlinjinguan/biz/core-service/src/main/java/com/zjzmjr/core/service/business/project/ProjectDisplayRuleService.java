package com.zjzmjr.core.service.business.project;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.project.ProjectDisplayRule;

/**
 * 项目一览展示条件表的业务处理类
 * 
 * @author oms
 * @version $Id: ProjectDisplayRuleService.java, v 0.1 2017-9-4 下午5:04:59 oms Exp $
 */
public interface ProjectDisplayRuleService {

    /**
     * 插入项目一览展示条件表
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertProjectDisplayRule(ProjectDisplayRule record);

    /**
     * 根据用户编号查询项目一览展示条件表
     * 
     * @param userId
     * @return
     */
    ResultEntry<ProjectDisplayRule> getProjectDisplayRuleByUserId(Integer userId);

    /**
     * 根据主键编号更新项目一览展示条件表
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateProjectDisplayRuleById(ProjectDisplayRule record);

}
