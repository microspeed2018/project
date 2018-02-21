package com.zjzmjr.admin.web.task.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.api.task.IProjectTaskFacade;

/**
 * 人事提醒任务类
 * 
 * @author Administrator
 * @version $Id: PersonnelAlertTask.java, v 0.1 2017-12-21 下午2:39:37 Administrator Exp $
 */
public class PersonnelAlertTask {

    private static final Logger logger = LoggerFactory.getLogger(PersonnelAlertTask.class);

    /**
     * 任务接口
     */
    @Resource(name = "projectTaskFacade")
    private IProjectTaskFacade projectTaskFacade;

    /**
     * 合同到期任务
     */
    public void expireExecute() {
        try {
            projectTaskFacade.contractExpireAlertTask(); // 合同到期提醒
            projectTaskFacade.practiceExpireAlertTask(); // 实习期到期提醒
            projectTaskFacade.probationExpireAlertTask(); // 试用期到期提醒
        } catch (Exception ex) {
            logger.error("执行任务错误", ex);
        }
    }
    
    /**
     * 生日提醒任务
     */
    public void birthdayExecute() {
        try {
            projectTaskFacade.birthdayAlertTask(); // 下个月生日提醒
        } catch (Exception ex) {
            logger.error("执行任务错误", ex);
        }
    }
}
