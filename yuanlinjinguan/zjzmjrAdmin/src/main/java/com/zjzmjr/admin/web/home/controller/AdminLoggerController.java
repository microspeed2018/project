/**
 * zjzmjr.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.admin.web.home.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * @author elliott
 * @version $Id: AdminLoggerController.java, v 0.1 2014-4-8 下午12:53:35 elliott
 *          Exp $
 */
public class AdminLoggerController extends BaseController {

    public static Logger adminLogger = LoggerFactory.getLogger("ADMIN_OPER_TRACK");

    public void logAdmin(String msg, Object... args) {
        if (adminLogger.isInfoEnabled() && StringUtils.isNotBlank(msg)) {
//            adminLogger.info((msg.contains("{admin}") ? msg : "[{admin}]" + msg).replace("{admin}", SpringSecurityUtil.getUserName()), args);
        }
    }
}
