package com.zjzmjr.core.service.business.project;

import java.util.List;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectSchema;
import com.zjzmjr.core.model.project.GardenProjectSchemaInfo;
import com.zjzmjr.core.model.project.ProjectSchemaQuery;

/**
 * 项目方案表的业务处理层
 * 
 * @author oms
 * @version $Id: GardenProjectSchemaService.java, v 0.1 2017-8-16 上午10:51:30 oms Exp $
 */
public interface GardenProjectSchemaService {

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
     * 更新项目方案表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProjectSchemaById(GardenProjectSchema record);
    
    /**
     * 批量插入项目方案表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertGardenProjectSchema(GardenProject project, List<GardenProjectSchema> record) throws ApplicationException;
        
    /**
     * 负责人申请方案时，更新项目方案表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProjectSchema(GardenProjectSchema record) throws ApplicationException;
    
    /**
     * 院办确立负责人时，更新项目方案表中的数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateGardenProjectSchema(GardenProjectSchemaInfo schemaInfo) throws ApplicationException;
    
}
