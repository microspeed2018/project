package com.zjzmjr.core.service.business.logo;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.logo.LogoManage;
import com.zjzmjr.core.model.logo.LogoManageQuery;


/**
 * 图标管理处理类
 * 
 * @author lenovo
 * @version $Id: LogoManageService.java, v 0.1 2016-9-20 下午5:08:18 lenovo Exp $
 */
public interface LogoManageService {

    /**
     * 图标管理一览
     * 
     * @param query
     * @return
     */
    ResultPage<LogoManage> getLogoManage(LogoManageQuery query);

    /**
     * 图标管理更新
     * 
     * @param logoManage
     * @return
     */
    ResultEntry<Integer> updateLogoManage(LogoManage logoManage);

    /**
     * 新增图标
     * 
     * @param logoManage
     * @return
     */
    ResultEntry<Integer> insertLogoManage(LogoManage logoManage);
    
    /**
     * 获取当前最大图标编号
     * 
     * @return
     */
    ResultEntry<Integer> getMaxLogoNo(Integer logoTypeNo);
    
    /**
     * 获取所有图标
     * 
     * @param query
     * @return
     */
    ResultRecord<LogoManage> getAllLogoManage(LogoManageQuery query);
    
    /**
     * 删除图标
     * 
     * @param id
     * @return
     */
    ResultEntry<Integer> deleteLogoManage(Integer id);
}
