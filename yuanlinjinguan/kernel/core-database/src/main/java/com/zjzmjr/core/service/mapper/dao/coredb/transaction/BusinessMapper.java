package com.zjzmjr.core.service.mapper.dao.coredb.transaction;

import java.util.List;

import com.zjzmjr.core.model.admin.Business;
import com.zjzmjr.core.model.admin.BusinessQuery;

/**
 * 事物DAO
 * 
 * @author Administrator
 * @version $Id: BusinessMapper.java, v 0.1 2016-7-12 下午1:26:06 Administrator Exp $
 */
public interface BusinessMapper {
    
    /**
     * 获取事物列表
     * 
     * @param query
     * @return
     */
    List<Business> getTransactionList(BusinessQuery query);

    /**
     * 新增事物
     * 
     * @param business
     * @return
     */
    int insertTransaction(Business business);

    /**
     * 更新事物
     * 
     * @param business
     * @return
     */
    int updateTransaction(Business business);
    
    /**
     * 根据扩展字段3更新事务表
     * 
     * @param business
     * @return
     */
    int updateTransactionByExtend3(Business business);

    /**
     * 根据用户ID 交易类型 创建时间 查询唯一数据
     * 
     * @param business
     * @return
     */
    Business getTransaction(Business business);

    /**
     * 根据用户ID 交易类型 查询列表数据
     * 
     * @param query
     * @return
     */
    List<Business> getRechargePostalList(BusinessQuery query);

    /**
     * 分页统计使用
     * 
     * @param query
     * @return
     */
    int getTransactionCount(BusinessQuery query);
    /**
     * 获取最近的失败的贷款事务
     * @param queryBusiness
     * @return
     */
    Business getFaliTransactionList(BusinessQuery queryBusiness);
}