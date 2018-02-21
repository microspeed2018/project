package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: GardenProjectMapper.java, v 0.1 2017-8-13 上午10:47:39 oms Exp $
 */
public interface GardenProjectMapper {
    
    int deleteGardenProjectById(Integer id);

    int insertGardenProject(GardenProject record);

    int getGardenProjectCount(GardenProjectQuery query);
    
    List<GardenProjectInfo> getGardenProjectByCondition(GardenProjectQuery query);

    /**
     * 获取表中最大的项目编号值
     * 
     * @return
     */
    String getGardenProjectMaxNo();
    
    int updateGardenProjectById(GardenProject record);

    /**
     * 根据主键和状态查询项目数据
     * 
     * @return
     */
    GardenProject getGardenProjectByIdAndStatus(GardenProjectQuery query);
    
    /**
     * 项目详细
     * 
     * @param query
     * @return
     */
    GardenProjectInfo getProjectDetail(GardenProjectQuery query);
    
    /**
     * 获取未录入合同的项目
     * 
     * @return
     */
    List<GardenProject> getGardenProjectNoContract();
    
    /**
     * 退保证金一览
     * 
     * @param query
     * @return
     */
    List<GardenProjectInfo> getGardenProjectCanBackBail(GardenProjectQuery query);
}