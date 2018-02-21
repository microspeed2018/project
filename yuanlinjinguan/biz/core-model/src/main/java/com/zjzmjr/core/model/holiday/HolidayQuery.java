package com.zjzmjr.core.model.holiday;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 节假日查询query
 * 
 * @author Administrator
 * @version $Id: HolidayQuery.java, v 0.1 2016-10-13 上午9:17:20 Administrator Exp
 *          $
 */
public class HolidayQuery extends BasePageQuery {

    private static final long serialVersionUID = 5510626938242501977L;

    /**
     * 节假日表主键ID
     */
    private Integer id;

    /**
     * 节假日时间
     */
    private String holidayTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayTime() {
        return holidayTime;
    }

    public void setHolidayTime(String holidayTime) {
        this.holidayTime = holidayTime;
    }

    @Override
    public String toString() {
        return "HolidayQuery [id=" + id + ", holidayTime=" + holidayTime + "]";
    }



}
