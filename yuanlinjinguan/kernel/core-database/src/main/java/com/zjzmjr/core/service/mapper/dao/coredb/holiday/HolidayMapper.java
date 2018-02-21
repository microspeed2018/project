package com.zjzmjr.core.service.mapper.dao.coredb.holiday;

import java.util.List;

import com.zjzmjr.core.model.holiday.Holiday;
import com.zjzmjr.core.model.holiday.HolidayQuery;

/**
 * 节假日表Mapper接口
 * 
 * @author chenning
 * @version $Id: HolidayMapper.java, v 0.1 2016-10-14 下午12:11:55 chenning Exp $
 */
public interface HolidayMapper {

    /**
     * 插入节假日日期
     * 
     * @param record
     * @return
     */
    int insertHoliday(Holiday holiday);
    
    /**
     * 根据日期查询是否是节假日
     * 
     * @param holiday
     * @return
     */
    Holiday queryHoliday(HolidayQuery query);
    
    /**
     * 更新节假日
     * 
     * @param holiday
     * @return
     */
    int updateHoliday(Holiday holiday);
    
    /**
     * 删除节假日
     * 
     * @param id
     * @return
     */
    int deleteByHolidayId(Integer id);
    
    /**
     * 获取节假日列表
     * 
     * @return
     */
    List<Holiday> getHolidayList(HolidayQuery query);
    
    /**
     * 分页查询用
     */
    int getHolidayCount(HolidayQuery query);

}