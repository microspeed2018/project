package com.zjzmjr.core.provider.task;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.task.IProjectTaskFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.common.biz.HolidayUtil;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.task.ProjectTaskService;

/**
 * 项目中所有的任务业务对外的接口
 * 
 * @author oms
 * @version $Id: ProjectTaskFacadeImpl.java, v 0.1 2017-9-14 上午10:22:21 oms Exp $
 */
@Service("projectTaskFacade")
public class ProjectTaskFacadeImpl implements IProjectTaskFacade {

    private static final Logger logger = LoggerFactory.getLogger(ProjectTaskFacadeImpl.class);

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    /** 项目任务 */
    @Resource(name = "projectTaskService")
    private ProjectTaskService projectTaskService;
    
    
    /**
     * 
     * @see com.zjzmjr.core.api.task.IProjectTaskFacade#applyDeadlineAlertTask()
     */
    @Override
    public ResultEntry<Integer> applyDeadlineAlertTask() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            // 获取需要提醒的项目
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            GardenProjectQuery query = new GardenProjectQuery();
            query.setMaxStep(GardenProjectStepEnum.P_50.getValue());
            query.setStep(GardenProjectStepEnum.P_40.getValue());
            query.setMinStep(GardenProjectStepEnum.P_30.getValue());
            query.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
            query.setApplyDeadlineStart(DateUtil.format(calendar.getTime(), DateUtil.YYYYMMDD));
            calendar.add(Calendar.DAY_OF_MONTH, 3);
            query.setApplyDeadlineEnd(DateUtil.format(calendar.getTime(), DateUtil.YYYYMMDD));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> projRst = gardenProjectService.getGardenProjectByCondition(query);
            if(projRst.isSuccess()){
                for (GardenProjectInfo project : projRst.getList()) {
                    try {
                        projectTaskService.applyDeadlineAlertTask(project, MessageContextEnum.applyDeadline, parseAlertStr(new Date(), DateUtil.parse(project.getApplyDeadline(), DateUtil.YYYYMMDD)));
                    } catch (ApplicationException ax){
                        logger.error(ax.getMessage(), ax);
                    }
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.task.IProjectTaskFacade#bidingDeadlineAlertTask()
     */
    @Override
    public ResultEntry<Integer> bidingDeadlineAlertTask() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            // 获取需要提醒的项目
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            GardenProjectQuery query = new GardenProjectQuery();
            query.setMaxStep(GardenProjectStepEnum.P_290.getValue());
            query.setStep(GardenProjectStepEnum.P_280.getValue());
            query.setMinStep(GardenProjectStepEnum.P_110.getValue());
            query.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
            query.setBidDeadlineStart(DateUtil.format(calendar.getTime(), DateUtil.YYYYMMDD));
            calendar.add(Calendar.DAY_OF_MONTH, 3);
            query.setBidDeadlineEnd(DateUtil.format(calendar.getTime(), DateUtil.YYYYMMDD));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> projRst = gardenProjectService.getGardenProjectByCondition(query);
            if(projRst.isSuccess()){
                for (GardenProjectInfo project : projRst.getList()) {
                    try {
                        projectTaskService.projectDeadlineAlertTask(project, MessageContextEnum.bidDeadline, parseAlertStr(new Date(), DateUtil.parse(project.getBiddingDeadline(), DateUtil.YYYYMMDD)));
                    } catch (ApplicationException ax){
                        logger.error(ax.getMessage(), ax);
                    }
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.task.IProjectTaskFacade#marginDeadlineAlertTask()
     */
    @Override
    public ResultEntry<Integer> marginDeadlineAlertTask() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            // 获取需要提醒的项目
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            GardenProjectQuery query = new GardenProjectQuery();
            query.setMaxStep(GardenProjectStepEnum.P_290.getValue());
            query.setStep(GardenProjectStepEnum.P_280.getValue());
            query.setMinStep(GardenProjectStepEnum.P_110.getValue());
            query.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
            query.setMarginDeadlineStart(DateUtil.format(calendar.getTime(), DateUtil.YYYYMMDD));
            calendar.add(Calendar.DAY_OF_MONTH, 3);
            query.setMarginDeadlineEnd(DateUtil.format(calendar.getTime(), DateUtil.YYYYMMDD));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> projRst = gardenProjectService.getGardenProjectByCondition(query);
            if(projRst.isSuccess()){
                for (GardenProjectInfo project : projRst.getList()) {
                    try {
                        projectTaskService.projectDeadlineAlertTask(project, MessageContextEnum.marginDeadline, parseAlertStr(new Date(), DateUtil.parse(project.getMarginDeadline(), DateUtil.YYYYMMDD)));
                    } catch (ApplicationException ax){
                        logger.error(ax.getMessage(), ax);
                    }
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.task.IProjectTaskFacade#tenderDeadlineAlertTask()
     */
    @Override
    public ResultEntry<Integer> tenderDeadlineAlertTask() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            // 获取需要提醒的项目
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            GardenProjectQuery query = new GardenProjectQuery();
            query.setMaxStep(GardenProjectStepEnum.P_290.getValue());
            query.setStep(GardenProjectStepEnum.P_280.getValue());
            query.setMinStep(GardenProjectStepEnum.P_110.getValue());
            query.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
            query.setTenderDeadlineStart(DateUtil.format(calendar.getTime(), DateUtil.YYYYMMDD));
            calendar.add(Calendar.DAY_OF_MONTH, 3);
            query.setTenderDeadlineEnd(DateUtil.format(calendar.getTime(), DateUtil.YYYYMMDD));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<GardenProjectInfo> projRst = gardenProjectService.getGardenProjectByCondition(query);
            if(projRst.isSuccess()){
                for (GardenProjectInfo project : projRst.getList()) {
                    try {
                        projectTaskService.projectDeadlineAlertTask(project, MessageContextEnum.tenderDeadline, parseAlertStr(new Date(), DateUtil.parse(project.getTenderDeadline(), DateUtil.YYYYMMDD)));
                    } catch (ApplicationException ax){
                        logger.error(ax.getMessage(), ax);
                    }
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 隔多少个工作日计算
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    private String parseAlertStr(Date startDate, Date endDate){
        startDate = com.zjzmjr.common.util.DateUtil.getPureDay(startDate);
        endDate = com.zjzmjr.common.util.DateUtil.getPureDay(endDate);
        int offset = 0;
        String alertStr = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        while(startDate.before(endDate)){
            if(!HolidayUtil.isHoliday(startDate)){
                offset++;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            startDate = calendar.getTime();
        }
        if(offset == 0){
            alertStr = "今天";
        }else{
            alertStr = offset + "个工作日后";
        }
        return alertStr;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.task.IProjectTaskFacade#contractExpireAlertTask()
     */
    @Override
    public ResultEntry<Integer> contractExpireAlertTask() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            projectTaskService.contractExpireAlertTask(MessageContextEnum.contractExpire, 1);
        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.task.IProjectTaskFacade#practiceExpireAlertTask()
     */
    @Override
    public ResultEntry<Integer> practiceExpireAlertTask() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            projectTaskService.contractExpireAlertTask(MessageContextEnum.practiceExpire, 1);
        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.task.IProjectTaskFacade#probationExpireAlertTask()
     */
    @Override
    public ResultEntry<Integer> probationExpireAlertTask() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            projectTaskService.contractExpireAlertTask(MessageContextEnum.probationExpire, 1);
        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.task.IProjectTaskFacade#birthdayAlertTask()
     */
    @Override
    public ResultEntry<Integer> birthdayAlertTask() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            projectTaskService.birthdayAlertTask();
        } catch (ApplicationException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
}
