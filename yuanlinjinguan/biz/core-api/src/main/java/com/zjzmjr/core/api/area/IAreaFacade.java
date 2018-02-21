package com.zjzmjr.core.api.area;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.area.AreaQuery;
import com.zjzmjr.core.model.area.InsuranceArea;
import com.zjzmjr.core.model.area.InsuranceAreaQuery;

/**
 * 
 * 
 * @author lenovo
 * @version $Id: IAreaFacade.java, v 0.1 2016-5-20 下午1:17:42 lenovo Exp $
 */
public interface IAreaFacade {
    
    /**
     * 获取所有省份
     * 
     * @return
     */
    ResultRecord<Area> getAllProv();
    
    /**
     * 获取所有市
     * 
     * @param provCode
     * @return
     */
    ResultRecord<Area> getAllDist(String provCode);
    
    /**
     * 依据条件查询
     * 
     * @param query
     * @return
     */
    ResultPage<Area> getAllArea(AreaQuery query);
    
    /**
     * 插入地区信息
     * 
     * @param area
     * @return
     */
    ResultEntry<Integer> insertArea(Area area);
    
    /**
     * 更新地区信息
     * 
     * @param area
     * @return
     */
    ResultEntry<Integer> updateArea(Area area);
    
    /**
     * 获取所有市县
     * 
     * @param provCode
     * @return
     */
    ResultRecord<Area> Dist(String provCode);
    
    /**
     * 获取地区市名称
     * 
     * @param provCode
     * @return
     */
    ResultRecord<Area> getCity(String provCode);
    
    /**
     * 获取地区县名称
     * 
     * @param provCode
     * @return
     */
    ResultRecord<Area> dist(Area area);
    
    /**
     * 获取地区市名称(有空行)
     * 
     * @param provCode
     * @return
     */
    ResultRecord<Area> getCityDist(String provCode);
    
    /**
     * 获取贷款地区县
     * 
     * @param area
     * @return
     */
    ResultRecord<Area> getLoanDist(Area area);
    
    ResultRecord<Area> getLoanCity(Area area);   

    /**
     * 获取所有地区（省市区分层）
     * 
     * @return
     */
    ResultRecord<Area> getAllNameArea();
}
