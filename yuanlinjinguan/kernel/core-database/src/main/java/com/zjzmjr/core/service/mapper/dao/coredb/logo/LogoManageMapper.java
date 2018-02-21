package com.zjzmjr.core.service.mapper.dao.coredb.logo;

import java.util.List;

import com.zjzmjr.core.model.logo.LogoManage;
import com.zjzmjr.core.model.logo.LogoManageQuery;

/**
 * 图标管理DAO层
 * 
 * @author lenovo
 * @version $Id: LogoManageMapper.java, v 0.1 2016-9-20 下午5:09:25 lenovo Exp $
 */
public interface LogoManageMapper {
    /**
     * 获取图标管理数量
     * 
     * @param query
     * @return
     */
    int getLogoManageCount(LogoManageQuery query);

    /**
     * 图标管理一览
     * 
     * @param query
     * @return
     */
    List<LogoManage> getLogoManage(LogoManageQuery query);

    /**
     * 图标更新
     * 
     * @param logoManage
     * @return
     */
    int updateLogoManage(LogoManage logoManage);

    /**
     * 新增图标
     */
    int insertLogoManage(LogoManage logoManage);
    
    /**
     * 获取当前最大的图标编号
     * 
     * @return
     */
    Integer getMaxLogoNo(Integer logoTypeNo);
    
    /**
     * 图标一览（不分页）
     * 
     * @param query
     * @return
     */
    List<LogoManage> getAllLogoManage(LogoManageQuery query);
    
    /**
     * 删除图标
     * 
     * @param id
     * @return
     */
    int deleteLogoManage(Integer id);
}
