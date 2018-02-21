package com.zjzmjr.core.api.holiday;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.holiday.Holiday;
import com.zjzmjr.core.model.holiday.HolidayQuery;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: IHolidayFacade.java, v 0.1 2016-10-12 下午2:00:06 Administrator
 *          Exp $
 */
public interface IHolidayFacade {

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
     * 删除节假日
     * 
     * @param holiday
     * @return
     */
    Result deleteHoliday(Holiday holiday);
    
    /**
     * 更新节假日
     * 
     * @param holiday
     * @return
     */
    Result updateHoliday(Holiday holiday);
    
    /**
     * 获取节假日列表
     * 
     * @param query
     * @return
     */
    ResultPage<Holiday> getHolidayList(HolidayQuery query);
    
}
