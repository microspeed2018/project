package com.zjzmjr.core.service.mapper.dao.coredb.area;

import java.util.List;

import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.area.AreaQuery;
/**
 * 地区模块DAO层
 * 
 * @author lenovo
 * @version $Id: AreaMapper.java, v 0.1 2016-7-13 下午2:26:03 lenovo Exp $
 */
public interface AreaMapper {

    int insertArea(Area area);

    int updateArea(Area area);
    
    List<Area> selectLoanProv();
    
    List<Area> selectLoanDist(String provCode);
    
    List<Area> selectInsuranceProv();
    
    List<Area> selectInsuranceDist(String provCode);
    
    List<Area> selectAllProv();
    
    List<Area> selectAllDist(String provCode);
    
    int getAllAreaCount(AreaQuery query);
    
    List<Area> getAllArea(AreaQuery query);
    
    List<Area> getAllDist(String provCode);
    
    List<Area> getCity(String provCode);
    
    List<Area> getDist(Area area);
    
    List<Area> getCityDist(String provCode);
    
    List<Area> getLoanCity(Area area);
    
    List<Area> getLoanDist(Area area);
    
    /**
     * 获取所有地区区分省市区层级
     * 
     * @return
     */
    List<Area> getAllNameArea();
}