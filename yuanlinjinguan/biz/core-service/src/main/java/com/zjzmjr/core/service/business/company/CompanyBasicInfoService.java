package com.zjzmjr.core.service.business.company;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.company.BasicDataQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyBasicInfoService.java, v 0.1 2017-8-10 下午6:08:54 oms Exp $
 */
public interface CompanyBasicInfoService {

    /**
     * 查询所有基础数据
     * 
     * @param id
     * @return
     */
    ResultRecord<BasicData> getAllBasicData();
    
    /**
     * 获取所有的类别
     * 
     * @return
     */
    ResultRecord<BasicData> getCategorys();
    
    /**
     * 获取基础数据一览
     * 
     * @param query
     * @return
     */
    ResultPage<BasicData> getBasicDataByCondition(BasicDataQuery query);
    
    /**
     * 根据Id获取基础数据
     * 
     * @param query
     * @return
     */
    ResultEntry<BasicData> getBasicDataById(BasicDataQuery query);
    
    /**
     * 插入基础数据
     * 
     * @param BasicData
     * @return
     */
    ResultEntry<Integer> insertBasicData(BasicData BasicData);
    
    /**
     * 更新基础数据
     * 
     * @param BasicData
     * @return
     */
    ResultEntry<Integer> updateBasicData(BasicData BasicData);
    
    /**
     * 删除基础数据
     * 
     * @param BasicData
     * @return
     */
    ResultEntry<Integer> deleteBasicDataById(Integer id);

}
