package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.GardenProjectSchema;
import com.zjzmjr.core.model.project.GardenProjectSchemaInfo;
import com.zjzmjr.core.model.project.ProjectSchemaQuery;

/**
 * 项目方案表
 * 
 * @author oms
 * @version $Id: GardenProjectSchemaMapper.java, v 0.1 2017-8-16 上午10:22:46 oms
 *          Exp $
 */
public interface GardenProjectSchemaMapper {

    int deleteGardenProjectSchemaById(Integer id);

    int insertGardenProjectSchema(GardenProjectSchema record);
    
    /**
     * 批量插入项目方案表
     * 
     * @param record
     * @return
     */
    int insertBatchGardenProjectSchema(List<GardenProjectSchema> record);

    int getGardenProjectSchemaCount(ProjectSchemaQuery query);

    List<GardenProjectSchemaInfo> getGardenProjectSchemaByCondition(ProjectSchemaQuery query);

    int updateGardenProjectSchemaById(GardenProjectSchema record);

}