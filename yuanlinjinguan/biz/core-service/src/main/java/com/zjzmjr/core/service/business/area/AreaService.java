package com.zjzmjr.core.service.business.area;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.area.AreaQuery;

/**
 * 银行卡地区服务层
 * 
 * @author lenovo
 * @version $Id: BankCardAreaService.java, v 0.1 2016-5-25 上午9:27:42 lenovo Exp $
 */
public interface AreaService {
   
    /**
     * 银行地区省份
     * 
     * @return
     */
    ResultRecord<Area> getAllProv();
    
    /**
     * 银行卡地区市县
     * 
     * @param provCode
     * @return
     */
    ResultRecord<Area> getAllDist(String provCode);
    
    /**
     * 获取所有地区
     * 
     * @param area
     * @return
     */
    ResultPage<Area> getAllArea(AreaQuery query);
    
    /**
     * 新增地区
     * 
     * @param area
     * @return
     */
    ResultEntry<Integer> insertArea(Area area);
    
    /**
     * 更新地区
     * 
     * @param area
     * @return
     */
    ResultEntry<Integer> updateArea(Area area);
    
    /**
     * 所有地区市县
     * 
     * @param provCode
     * @return
     */
    ResultRecord<Area> getDist(String provCode);
    
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
     * 获取贷款地区市
     * 
     * @param area
     * @return
     */
    ResultRecord<Area> getLoanCity(Area area);
    
    /**
     * 获取贷款地区县
     * 
     * @param area
     * @return
     */
    ResultRecord<Area> getLoanDist(Area area);
    
    /**
     * 获取所有地区（省市区分层）
     * 
     * @return
     */
    ResultRecord<Area> getAllNameArea();
}
