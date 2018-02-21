package com.zjzmjr.core.service.business.holiday.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.holiday.Holiday;
import com.zjzmjr.core.model.holiday.HolidayQuery;
import com.zjzmjr.core.service.business.holiday.HolidayService;
import com.zjzmjr.core.service.mapper.dao.coredb.holiday.HolidayMapper;

/**
 * 节假日维护service层
 * 
 * @author chenning
 * @version $Id: HolidayServiceImpl.java, v 0.1 2016-10-12 下午1:30:59
 *          chenning Exp $
 */
@Service("holidayService")
public class HolidayServiceImpl implements HolidayService {

    private static final Logger logger = LoggerFactory.getLogger(HolidayServiceImpl.class);

    /**
     * 节假日表DAO层接口
     */
    @Resource(name = "holidayMapper")
    private HolidayMapper holidayMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.holiday.HolidayService#addHoliday(com.zjzmjr.core.model.holiday.Holiday)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result addHoliday(Holiday holiday) {
        logger.debug("addHoliday入参:{}",holiday);
        Result result = new Result();
        //如果没有设置节假日时间，则不做插入
        if (Util.isNull(holiday) || StringUtils.isBlank(holiday.getHolidayTime())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat("{}"), holiday);
            logger.debug("addHoliday出参:{}",result);
            return result;
        }
        int total = holidayMapper.insertHoliday(holiday);
        if (total > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":添加节假日失败");
        }
        logger.debug("addHoliday出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.holiday.HolidayService#queryHoliday(com.zjzmjr.core.model.holiday.Holiday)
     */
    @Override
    public ResultEntry<Holiday> queryHoliday(HolidayQuery query) {
        logger.debug("queryHoliday入参:{}",query);
        ResultEntry<Holiday> result = new ResultEntry<Holiday>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat("{}"), query);
            logger.debug("queryHoliday出参:{}",result);
            return result;
        }
        Holiday val = holidayMapper.queryHoliday(query);
        if (Util.isNotNull(val)) {
            result.setSuccess(true);
            result.setT(val);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        logger.debug("queryHoliday出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.holiday.HolidayService#deleteHoliday(com.zjzmjr.core.model.holiday.Holiday)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result deleteHoliday(Holiday holiday) {
        logger.debug("deleteHoliday入参:{}",holiday);
        Result result = new Result();
        //如果没有设置ID则不进行删除
        if (Util.isNull(holiday) || Util.isNull(holiday.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat("{}"), holiday);
            logger.debug("deleteHoliday出参:{}",result);
            return result;
        }
        int total = holidayMapper.deleteByHolidayId(holiday.getId());
        if (total > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName().concat("id:" + holiday.getId()));
        }
        logger.debug("deleteHoliday出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.holiday.HolidayService#updateHoliday(com.zjzmjr.core.model.holiday.Holiday)
     */
    @Override
    public Result updateHoliday(Holiday holiday) {
        logger.debug("updateHoliday入参:{}",holiday);
        Result result = new Result();
        //如果没有设置ID则不进行更新
        if (Util.isNull(holiday) || Util.isNull(holiday.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat("{}"), holiday.toString());
            logger.debug("updateHoliday出参:{}",result);
            return result;
        }
        int total = holidayMapper.updateHoliday(holiday);
        if (total > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新节假日失败");
        }
        logger.debug("updateHoliday出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.holiday.HolidayService#getHolidayList()
     */
    @Override
    public ResultPage<Holiday> getHolidayList(HolidayQuery query) {
        logger.debug("getHolidayList入参:{}",query);
        ResultPage<Holiday> result = new ResultPage<Holiday>();
        //先根据条件查询库里是否有记录
        int total = holidayMapper.getHolidayCount(query);
        //当查询条数大于0才进行查询操作
        if (total > 0) {
            List<Holiday> holidays = holidayMapper.getHolidayList(query);
            result.setSuccess(true);
            result.setList(holidays);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        //设置分页参数
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getHolidayList出参:{}",result);
        return result;
    }

}
