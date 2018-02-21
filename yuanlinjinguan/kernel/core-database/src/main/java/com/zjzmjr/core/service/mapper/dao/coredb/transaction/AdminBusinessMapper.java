package com.zjzmjr.core.service.mapper.dao.coredb.transaction;

import java.util.List;

import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminBusinessQuery;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: AdminBusinessMapper.java, v 0.1 2016-10-9 下午2:17:37 Administrator Exp $
 */
public interface AdminBusinessMapper {
    
    /**
     * 新增管理员事物
     * 
     * @param adminBusiness
     * @return
     */
    int insertAdminTransaction(AdminBusiness adminBusiness);
    
    /**
     * 更新管理员事物
     */
    int updateAdminTransaction(AdminBusiness adminBusiness);
    
    /**
     * 根据管理员姓名 事物类型 查询管理员事物列表
     * 
     * @return
     */
    List<AdminBusiness> getAdminTransactionList(AdminBusinessQuery query);
    
    /**
     * 分页统计使用
     * 
     * @param query
     * @return
     */
    int getAdminTransactionCount(AdminBusinessQuery query);
    
}