package com.zjzmjr.core.service.business.app;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.app.AppRelease;
import com.zjzmjr.core.model.app.AppReleaseQuery;

/**
 * app发布管理服务层
 * 
 * @author chenning
 * @version $Id: AppReleaseService.java, v 0.1 2016-11-17 上午11:01:09 Administrator Exp $
 */
public interface AppReleaseService {
    
    /**
     * 获取app版本信息一览
     * 
     * @param query
     * @return
     */
    ResultPage<AppRelease> getAppReleases(AppReleaseQuery query);
    
    /**
     * 根据Id获取app版本信息
     * 
     * @param query
     * @return
     */
    ResultEntry<AppRelease> getAppReleaseById(AppReleaseQuery query);
    
    /**
     * 发布app版本
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertAppRelease(AppRelease record);
    
    /**
     * 更新app版本
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateById(AppRelease record);
    
}
