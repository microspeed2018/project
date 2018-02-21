package com.zjzmjr.admin.web.task.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.api.task.IProjectTaskFacade;

/**
 * 任务工具类
 * 
 * @author oms
 * @version $Id: ApplyDeadlineAlertTask.java, v 0.1 2017-9-14 下午7:33:59 oms Exp $
 */
public class ApplyDeadlineAlertTask {

    private static final Logger logger = LoggerFactory.getLogger(ApplyDeadlineAlertTask.class);
    
    /**
     * 任务接口
     */
    @Resource(name = "projectTaskFacade")
    private IProjectTaskFacade projectTaskFacade;
    
    /**
     * 执行任务
     */
    public void execute(){
        try {
            projectTaskFacade.applyDeadlineAlertTask();
            projectTaskFacade.bidingDeadlineAlertTask();
            projectTaskFacade.marginDeadlineAlertTask();
            projectTaskFacade.tenderDeadlineAlertTask();
        } catch (Exception ex) {
            logger.error("执行任务错误", ex);
        }
    }
}
