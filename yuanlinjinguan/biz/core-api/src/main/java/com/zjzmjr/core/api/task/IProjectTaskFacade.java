package com.zjzmjr.core.api.task;

import com.zjzmjr.core.base.result.ResultEntry;

/**
 * 项目中所有的任务业务对外的接口
 * 
 * @author oms
 * @version $Id: IProjectTaskFacade.java, v 0.1 2017-9-14 上午10:15:45 oms Exp $
 */
public interface IProjectTaskFacade {

    /**
     * 报名截止日期前3天，提醒经营专员，经营经理，技术负责人：【项目{}即将于3天后报名截止，请注意！】
     * 
     * @return
     */
    ResultEntry<Integer> applyDeadlineAlertTask();
    
    /**
     * 招标报名截止日期前3天，提醒经营专员，经营经理，技术负责人：【项目{}即将于3天后报名截止，请注意！】
     * 
     * @return
     */
    ResultEntry<Integer> bidingDeadlineAlertTask();
    
    /**
     * 保证金截止日期前3天，提醒经营专员，经营经理，技术负责人：【项目{}即将于3天后报名截止，请注意！】
     * 
     * @return
     */
    ResultEntry<Integer> marginDeadlineAlertTask();
    
    /**
     *  投标截止日期前3天，提醒经营专员，经营经理，技术负责人：【项目{}即将于3天后报名截止，请注意！】
     * 
     * @return
     */
    ResultEntry<Integer> tenderDeadlineAlertTask();
    
    /**
     * 员工合同到期前一个月，提醒办公室主任：【以下员工({})的合同即将于{}结束，请注意】
     * 
     * @return
     */
    ResultEntry<Integer> contractExpireAlertTask();
    
    /**
     * 实习期到期前一个月，提醒办公室主任：【以下员工({})的实习期即将于{}结束，请注意】
     * 
     * @return
     */
    ResultEntry<Integer> practiceExpireAlertTask();
    
    /**
     * 试用期到期前一个月，提醒办公室主任：【以下员工({})的试用期即将于{}结束，请注意】
     * 
     * @return
     */
    ResultEntry<Integer> probationExpireAlertTask();
    
    /**
     * 生日提醒，提醒办公室主任：【{}月生日员工名单:{}，请了解】
     * 
     * @return
     */
    ResultEntry<Integer> birthdayAlertTask();
    
    
    
}
