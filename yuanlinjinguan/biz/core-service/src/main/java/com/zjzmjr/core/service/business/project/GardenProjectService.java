package com.zjzmjr.core.service.business.project;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;

/**
 * 项目工程的服务层
 * 
 * @author oms
 * @version $Id: GardenProjectService.java, v 0.1 2017-8-13 下午9:11:05 oms Exp $
 */
public interface GardenProjectService {

    /**
     * 
     * 
     * @param query
     * @return
     */
    ResultPage<GardenProjectInfo> getGardenProjectByCondition(GardenProjectQuery query);

    /**
     * 获取项目表中最大的项目编号值，然后最大项目编号值加一返回出去
     * 
     * @return
     */
    ResultEntry<String> getGardenProjectMaxNo();
    
    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertGardenProject(GardenProject record) throws ApplicationException;

    /**
     * 通过主键修改项目信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProjectById(GardenProject record);
    
    /**
     * 修改项目信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProject(GardenProject record) throws ApplicationException;
    
    /**
     * 删除项目信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> deleteGardenProject(Integer id) ;
    
    /**
     * 合同操作时修改项目信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertProjectByContract(GardenProject record);
    
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
    ResultEntry<Integer> applyBackBail(GardenProjectQuery query) throws ApplicationException ;
   
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
    * @throws ApplicationException
    */
    ResultEntry<Integer> updateProjectSchema(GardenProject record) throws ApplicationException;

}
