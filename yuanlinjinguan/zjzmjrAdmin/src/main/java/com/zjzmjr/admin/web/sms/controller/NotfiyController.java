/**
 * zjzmjr.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.admin.web.sms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.sms.form.NotifyForm;
import com.zjzmjr.core.api.sms.INotifyFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.sms.Notify;
import com.zjzmjr.core.model.sms.NotifyQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 
 * @author Administrator
 * @version $Id: NotfiyController.java, v 0.1 2015-11-4 下午2:08:18 Administrator
 *          Exp $
 */
@Controller
@RequestMapping("/notify/user")
public class NotfiyController extends BaseController {

    private final static String index = "/WEB-INF/pages/sms/notify.jsp";

    private static final Logger logger = LoggerFactory.getLogger(NotfiyController.class);
    
    @Autowired
    private INotifyFacade notifyFacade;

    @RequestMapping("/index.htm")
    public String index(ModelMap model) {
        model.put("smsSendAuth", SpringSecurityUtil.hasAuth(UserAuthParams.SMS_SEND));
        return index;
    }

    //@Security(auth = "SMS_SEND", checkSource = true)
    @RequestMapping(value = "/list.htm", method = RequestMethod.POST)
    public void querypage(HttpServletResponse resp,  HttpServletRequest req, NotifyForm form) throws IOException {
        logger.debug("querypage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            NotifyQuery query = new NotifyQuery();
            if(StringUtils.isNotBlank(form.getUserName())){
                query.setUserName(form.getUserName());
            }
            if(StringUtils.isNotBlank(form.getDestination())){
                query.setDestination(form.getDestination());
            }
            if(StringUtils.isNotBlank(form.getCause())){
                query.setCause(form.getCause());
            }
            if(StringUtils.isNotBlank(form.getContent())){
                query.setContent(form.getContent());
            }
            if(Util.isNotNull(form.getStatus())){
                query.setStatus(form.getStatus());
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            if (StringUtils.isBlank(form.getMessageDate())) {
                query.setCreateTime(null);
            } else{
                query.setCreateTime(DateUtil.parse(form.getMessageDate(), DateUtil.YY_MM_DD));
            }
            if (StringUtils.isBlank(form.getMessageDateEnd())) {
                query.setLastTime(null);
            } else{
                query.setLastTime(DateUtil.parse(form.getMessageDateEnd(), DateUtil.YY_MM_DD));
            }
            ResultPage<Notify> result = notifyFacade.queryPage(query);
            logger.debug("querypage 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("短信一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

//    @RequestMapping(value = "/user/saveNotify.htm", method = RequestMethod.POST)
//    public void saveNotify(HttpServletResponse resp, NotifyForm form, HttpServletRequest req, BindingResult bindingResult) throws Exception {
//        logger.debug("saveNotify 执行开始  入参:{}", form);
//        Notify notify = new Notify();
//        notify.setUserId(0);//userId=0时，手机号直接取自传的值，否则根据userId数据库中拉取
//        notify.setType(2);
//        notify.setDestination(form.getDestination());
//        notify.setTitle("");
//        notify.setContent(form.getContent());
//        notify.setCause(form.getCause());
//        notify.setFailCount(0);
//        notify.setPriority(form.getPriority());
//        notify.setStatus(NotifyStatusEnum.WAIT.getValue());
//        Date sendTime = DateUtil.parse(form.getSendTime(), DateUtil.YYMMDDHHMMMSS);
//        notify.setScheduleTime(sendTime);
//        notify.setSendTime(sendTime);
//        notify.setCreateTime(new Date());
//        notify.setSourceType(SourceTypeEnum.UNKNOW.getValue());
//        notify.setSourceId(0);
//        Map<String, Object> map = new HashMap<String, Object>();
//        //新增管理员事物
//        AdminBusiness adminBusiness=new AdminBusiness();
//        adminBusiness.setBusinessType(AdminBusinessTypeEnum.SEND_SMS.getValue());
//        ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
//        try {
//            Result result = notifyFacade.save(notify);
//            logger.debug("saveNotify 执行结束  出参:{}", result);
//            map.put("success", result.isSuccess());
//            map.put("resultMsg", result.getErrorMsg());
//            JsonUtil.printJSON(map, resp);
//            adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
//        } catch (Exception e) {
//            map.put("resultMsg", "参数错误");
//            JsonUtil.printJSON(map, resp);
//            adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
//            adminBusiness.setComment("参数错误");
//        }
//        //更新管理员事物
//        adminBusiness.setId(val.getT().getId());
//        AdminTransactionUtil.updateAdminTransaction(adminBusiness);
//    }
}
