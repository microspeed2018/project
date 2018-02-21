package com.zjzmjr.core.service.business.holiday;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.holiday.Holiday;
import com.zjzmjr.core.model.holiday.HolidayQuery;

/**
 * 
 * 节假日业务层
 * @author Administrator
 * @version $Id: HolidayService.java, v 0.1 2016-10-12 下午1:26:28 Administrator Exp $
 */
public interface HolidayService {
    
    /**
     * 添加节假日
     * 
     * @param holiday
     * @return
     */
    Result addHoliday(Holiday holiday);
    
    /**
     * 查询节假日
     * 
     * @param holiday
     * @return
     */
    ResultEntry<Holiday> queryHoliday(HolidayQuery query);
    
    /**
     * 更新节假日
     * 
     * @param holiday
     * @return
     */
    Result updateHoliday(Holiday holiday);
    
    /**
     * 删除节假日
     * 
     * @param holiday
     * @return
     */
    Result deleteHoliday(Holiday holiday);
    
    /**
     * 获取节假日列表
     * 
     * @return
     */
    ResultPage<Holiday> getHolidayList(HolidayQuery query);
}
