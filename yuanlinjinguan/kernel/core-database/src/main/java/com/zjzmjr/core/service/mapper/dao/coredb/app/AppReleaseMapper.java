package com.zjzmjr.core.service.mapper.dao.coredb.app;

import java.util.List;

import com.zjzmjr.core.model.app.AppRelease;
import com.zjzmjr.core.model.app.AppReleaseQuery;

/**
 * t_app_releaseApp发布管理表DAO
 * 
 * @author Administrator
 * @version $Id: AppReleaseMapper.java, v 0.1 2016-11-17 上午10:27:42 Administrator Exp $
 */
public interface AppReleaseMapper {
    
    /**
     * 统计app版本信息数量
     * 
     * @param query
     * @return
     */
    int getAppReleaseCount(AppReleaseQuery query);
    
    /**
     * 获取app版本信息
     * 
     * @param query
     * @return
     */
    List<AppRelease> getAppReleases(AppReleaseQuery query);
    
    /**
     * 根据Id获取app版本信息
     * 
     * @param query
     * @return
     */
    AppRelease getAppReleaseById(AppReleaseQuery query);
    
    /**
     * 根据Id删除app版本信息
     * 
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 插入app版本信息
     * 
     * @param record
     * @return
     */
    int insertAppRelease(AppRelease record);
    
    /**
     * 根据Id更新app版本信息
     * 
     * @param record
     * @return
     */
    int updateById(AppRelease record);
}