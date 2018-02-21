package com.zjzmjr.core.common.biz;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.zjzmjr.common.util.DateUtil;
import com.zjzmjr.core.api.holiday.IHolidayFacade;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.SpringContextUtil;
import com.zjzmjr.core.model.holiday.Holiday;
import com.zjzmjr.core.model.holiday.HolidayQuery;

/**
 * 节假日工具类
 * 
 * @author Administrator
 * @version $Id: HolidayUtil.java, v 0.1 2016-10-12 下午2:13:54 Administrator Exp
 *          $
 */
public class HolidayUtil {

    private static IHolidayFacade holidayFacade = SpringContextUtil.getBean("holidayFacade");

    /**
     * 节假日枚举类型
     * 
     * @author oms
     * @version $Id: HolidayUtil.java, v 0.1 2017-5-3 下午4:30:19 oms Exp $
     */
    private enum HolidayTypeEnum {

        REST_DAYS_WORK(0, "调休工作日"),

        REST_DAY(1, "成功");

        private Integer value;

        private String message;

        private HolidayTypeEnum(Integer value, String message) {
            this.value = value;
            this.message = message;
        }

        public Integer getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }

        public static HolidayTypeEnum getByValue(Integer value) {
            if (value != null) {
                for (HolidayTypeEnum enu : values()) {
                    if (enu.value.equals(value)) {
                        return enu;
                    }
                }
            }
            return null;
        }
    }

    /**
     * 判断是否是周末
     * 
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        return weekday == Calendar.SATURDAY || weekday == Calendar.SUNDAY;
    }

    /**
     * 判断是否是国家规定节假日
     * 
     * @param date
     * @return
     */
    public static boolean isFestivals(Date date) {
        HolidayQuery query = new HolidayQuery();
        query.setHolidayTime(com.zjzmjr.core.base.util.DateUtil.format(date, com.zjzmjr.core.base.util.DateUtil.YYMMDD));
        ResultEntry<Holiday> result = holidayFacade.queryHoliday(query);
        if (result.isSuccess()) {
            Holiday holiday = result.getT();
            if (HolidayTypeEnum.REST_DAYS_WORK.getValue().equals(HolidayTypeEnum.getByValue(holiday.getFlag()))) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是休息日
     * 
     * @param date
     * @return
     */
    public static boolean isHoliday(Date date) {

        return isWeekend(date) || isFestivals(date);

    }
    
    /**
     * 计算两个时间之间相差的小时数
     * 
     * @param dateFrom
     * @param dateTo
     * @param incHoliday  是否包括节假日
     * @param tailUp  不足一小时是否按一小时计算
     * @return
     */
    public static int getOffsetHours(Date dateFrom, Date dateTo, boolean incHoliday, boolean tailUp){
        int offset = 0;
        int amount = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);
        while(!incHoliday && isHoliday(calendar.getTime())){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dateFrom = DateUtil.getPureDay(calendar.getTime());
        }
        if(dateFrom.compareTo(dateTo) <= 0){
            calendar.setTime(dateFrom);
            do{
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                if(incHoliday && isHoliday(calendar.getTime())){
                    amount += 24;
                }else if(!isHoliday(calendar.getTime())){
                    amount += 24;
                }
            }while(calendar.getTime().compareTo(dateTo) <= 0);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            dateFrom = calendar.getTime();
            amount = amount - 24;
            offset = DateUtil.getOffsetHours(dateFrom, dateTo, tailUp);
        }
        return amount + offset;
    }
    
    public static void main(String[] args) {
        Date dateFrom;
        try {
            dateFrom = DateUtil.parseDate("2017-07-29 11:20:20", "yyyy-MM-dd hh:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFrom);
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            Date dateTo = calendar.getTime();
            System.out.println(getOffsetHours(dateFrom, dateTo, true, true));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
