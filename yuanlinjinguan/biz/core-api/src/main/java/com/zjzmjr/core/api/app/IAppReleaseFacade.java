package com.zjzmjr.core.api.app;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.app.AppRelease;
import com.zjzmjr.core.model.app.AppReleaseQuery;

/**
 * app发布管理处理类
 * 
 * @author chenning
 * @version $Id: IAppReleaseFacade.java, v 0.1 2016-11-17 上午11:17:52 Administrator Exp $
 */
public interface IAppReleaseFacade {

    /**
     * 获取app版本信息一览
     * 
     * @param query
     * @return
     */
    ResultPage<AppRelease> getAppReleases(AppReleaseQuery query);
    
    /**
     * 根据Id查询
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
     * 根据Id更新app版本信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateAppRelease(AppRelease record);
}
