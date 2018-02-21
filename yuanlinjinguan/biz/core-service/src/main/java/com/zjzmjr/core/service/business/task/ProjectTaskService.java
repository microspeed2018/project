package com.zjzmjr.core.service.business.task;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.model.project.GardenProjectInfo;

/**
 * 项目中所有任务的服务业务处理
 * 
 * @author oms
 * @version $Id: ProjectTaskService.java, v 0.1 2017-9-13 下午7:13:54 oms Exp $
 */
public interface ProjectTaskService {

    /**
     * 报名截止日期前3天，提醒经营专员，经营经理，技术负责人：【项目{}即将于3天后报名截止，请注意！】
     * 
     * @param project
     * @param messageEnum  消息枚举
     * @param Offset  间隔天数
     * @return
     * @throws ApplicationException
     */
    ResultEntry<Integer> applyDeadlineAlertTask(GardenProjectInfo project, MessageContextEnum messageEnum, String offset) throws ApplicationException;

    /**
     * 招标报名截止日期， 保证金截止日期 ，投标截止日期  ： 前3天，提醒经营专员，经营经理，技术负责人：【项目{}即将于3天后报名截止，请注意！】
     * 
     * @param project
     * @param messageEnum
     *            消息枚举
     * @param Offset
     *            间隔天数
     * @return
     * @throws ApplicationException
     */
    ResultEntry<Integer> projectDeadlineAlertTask(GardenProjectInfo project, MessageContextEnum messageEnum, String offset) throws ApplicationException;
    
    /**
     * 员工合同到期前一个月，提醒人事：【以下员工({})的合同即将于{}结束，请注意】
     * 
     * @return
     */
    ResultEntry<Integer> contractExpireAlertTask(MessageContextEnum messageEnum, Integer offset) throws ApplicationException;
    
    /**
     * 
     * 
     * @return
     * @throws ApplicationException
     */
    ResultEntry<Integer> birthdayAlertTask() throws ApplicationException;
    
}
