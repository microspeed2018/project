package com.zjzmjr.core.provider.holiday;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.holiday.IHolidayFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.holiday.Holiday;
import com.zjzmjr.core.model.holiday.HolidayQuery;
import com.zjzmjr.core.service.business.holiday.HolidayService;

/**
 * 
 * 节假日维护Facade层
 * @author chenning
 * @version $Id: HolidayFacadeImpl.java, v 0.1 2016-10-12 下午2:10:28 chenning Exp $
 */
@Service("holidayFacade")
public class HolidayFacadeImpl implements IHolidayFacade{

    private static final Logger logger=LoggerFactory.getLogger(HolidayFacadeImpl.class);
    
    @Resource(name="holidayService")
    private HolidayService holidayService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.holiday.IHolidayFacade#addHoliday(com.zjzmjr.core.model.holiday.Holiday)
     */
    @Override
    public Result addHoliday(Holiday holiday) {
        Result result=new Result();
        try {
            //先查询数据库里是否有相同的节假日数据
            HolidayQuery query=new HolidayQuery();
            query.setHolidayTime(holiday.getHolidayTime());
            Result fes=holidayService.queryHoliday(query);
            //如果有数据，则不插入新的节假日
            if(fes.isSuccess()){
                result.setSuccess(false);
                result.setErrorMsg(ErrorCodeEnum.RECORD_EXSIST.getName());
                result.setErrorCode(ErrorCodeEnum.RECORD_EXSIST.getCode());
            }else{
                //数据库没有相同数据才插入新的数据
                result=holidayService.addHoliday(holiday);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.holiday.IHolidayFacade#queryHoliday(com.zjzmjr.core.model.holiday.Holiday)
     */
    @Override
    public ResultEntry<Holiday> queryHoliday(HolidayQuery query) {
        ResultEntry<Holiday> result = new ResultEntry<Holiday>();
        try {
            result = holidayService.queryHoliday(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.holiday.IHolidayFacade#deleteHoliday(com.zjzmjr.core.model.holiday.Holiday)
     */
    @Override
    public Result deleteHoliday(Holiday holiday) {
        Result result=new Result();
        try {
            result=holidayService.deleteHoliday(holiday);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.holiday.IHolidayFacade#updateHoliday(com.zjzmjr.core.model.holiday.Holiday)
     */
    @Override
    public Result updateHoliday(Holiday holiday) {
        Result result=new Result();
        try {
            //先查询数据库里是否有相同的节假日数据
            HolidayQuery query=new HolidayQuery();
            query.setHolidayTime(holiday.getHolidayTime());
            Result fes=holidayService.queryHoliday(query);
            //如果有数据，则不更新新的节假日
            if(fes.isSuccess()){
                result.setSuccess(false);
                result.setErrorMsg(ErrorCodeEnum.RECORD_EXSIST.getName());
                result.setErrorCode(ErrorCodeEnum.RECORD_EXSIST.getCode());
            }else{
                //数据库没有相同记录才能更新
                result=holidayService.updateHoliday(holiday);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.holiday.IHolidayFacade#getHolidayList(com.zjzmjr.core.model.holiday.HolidayQuery)
     */
    @Override
    public ResultPage<Holiday> getHolidayList(HolidayQuery query) {
        ResultPage<Holiday> result=new ResultPage<Holiday>();
        try {
            result=holidayService.getHolidayList(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

}
