package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;

import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.company.BasicDataQuery;

/**
 * 基础数据表t_basic的DAO
 * 
 * @author Administrator
 * @version $Id: BasicMapper.java, v 0.1 2016-10-26 上午10:40:57 Administrator Exp $
 */
public interface CompanyBasicInfoMapper {

    /**
     * 统计基础数据数量
     * 
     * @param query
     * @return
     */
    int getBasicCount(BasicDataQuery query);
    
    /**
     * 获取基础数据一览
     * 
     * @param query
     * @return
     */
    List<BasicData> getBasic(BasicDataQuery query);
    
    /**
     * 根据Id获取基础数据
     * 
     * @param id
     * @return
     */
    BasicData getBasicById(BasicDataQuery query);
    
    /**
     * 根据Id删除基础数据
     * 
     * @param id
     * @return
     */
    int deleteById(Integer id);
    
    /**
     * 新增基础数据
     * 
     * @param BasicData
     * @return
     */
    int insertBasic(BasicData BasicData);
    
    /**
     * 更新基础数据
     * 
     * @param BasicData
     * @return
     */
    int updateById(BasicData BasicData);
    
    /**
     * 获取所有的类别
     * 
     * @return
     */
    List<BasicData> getCategorys();
    
    List<BasicData> getAllBasic();
    
    /**
     * 获取银行利率所需基础数据
     * 
     * @param query
     * @return
     */
    List<BasicData> getBankRateBasic(BasicDataQuery query);
}
