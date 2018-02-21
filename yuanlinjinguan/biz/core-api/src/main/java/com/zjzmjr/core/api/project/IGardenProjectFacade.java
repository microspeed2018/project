package com.zjzmjr.core.api.project;

import java.util.List;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.GardenProjectSchema;
import com.zjzmjr.core.model.project.GardenProjectSchemaInfo;
import com.zjzmjr.core.model.project.ProjectDisplayRule;
import com.zjzmjr.core.model.project.ProjectSchemaQuery;

/**
 * 项目表接口
 * 
 * @author oms
 * @version $Id: IGardenProjectFacade.java, v 0.1 2017-8-14 下午1:12:46 oms Exp $
 */
public interface IGardenProjectFacade {

    /**
     * 获取项目表中最大的项目编号值，然后最大项目编号值加一返回出去
     * 
     * @return
     */
    ResultEntry<String> getGardenProjectMaxNo();
    
    /**
     * 根据条件查询项目表中的数据
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectInfo> getGardenProjectByCondition(GardenProjectQuery query);

    /**
     * 插入项目表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertGardenProject(GardenProject record);

    /**
     * 更新项目表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProjectById(GardenProject record);

    /**
     * 根据条件查询项目方案表中的数据
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectSchemaInfo> getGardenProjectSchemaByCondition(ProjectSchemaQuery query);

    /**
     * 插入项目方案表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertGardenProjectSchema(GardenProjectSchema record);

    /**
     * 批量插入项目方案表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertGardenProjectSchema(GardenProject project, List<GardenProjectSchema> record);

    /**
     * 更新项目方案表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProjectSchema(GardenProjectSchema record);

    /**
     * 院办确立负责人时，更新项目方案表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProjectSchema(GardenProjectSchemaInfo schemaInfo);
    
    /**
     * 根据用户编号查询项目一览展示条件表
     * 
     * @param userId
     * @return
     */
    ResultEntry<ProjectDisplayRule> getProjectDisplayRuleByUserId(Integer userId);

    /**
     * 插入项目一览展示条件表
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> generateProjectDisplayRule(ProjectDisplayRule record);
   
    /**
     * 修改项目信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProject(GardenProject record);
    
    /**
     * 根据主键和状态查询项目数据
     * 
     * @param query
     * @return
     */
    ResultEntry<GardenProject> getGardenProjectByIdAndStatus(GardenProjectQuery query);
    
    /**
     * 项目详细
     * 
     * @param query
     * @return
     */
    ResultEntry<GardenProjectInfo> getProjectDetail(GardenProjectQuery query);
    
    /**
     * 获取未录入合同的项目
     * 
     * @return
     */
    ResultRecord<GardenProject> getGardenProjectNoContract();
    
    /**
     * 申请退保证金
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> applyBackBail(GardenProjectQuery query);
    
    /**
     * 退保证金一览
     * 
     * @param query
     * @return
     */
    ResultRecord<GardenProjectInfo> getGardenProjectCanBackBail(GardenProjectQuery query);
    
    /**
     * 立项审核不通过更新方法
     * 
     * @param record
     * @return
     */
     ResultEntry<Integer> updateProjectSchema(GardenProject record);
}
