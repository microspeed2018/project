package com.zjzmjr.admin.web.message.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.message.form.MessageForm;
import com.zjzmjr.core.api.message.IMessageFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.MessageInfo;
import com.zjzmjr.core.model.message.MessageQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 后台管理消息模块控制层
 * 
 * @author lenovo
 * @version $Id: MessageController.java, v 0.1 2016-7-13 下午2:34:42 lenovo Exp $
 */

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
    
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    
    private final static String index = "/WEB-INF/pages/message/message.jsp";
    
    @RequestMapping(value="/index.htm" ,method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("messSendAuth", SpringSecurityUtil.hasAuth(UserAuthParams.MESS_SEND));
        return index;
    }
   
    @Resource(name="messageFacade")
    private IMessageFacade messageFacade;
    
    /**
     * 根据条件获取消息列表
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/getAllMessage.htm", method = RequestMethod.POST)
    public void getAllMessage( HttpServletResponse resp, MessageForm form){
        logger.debug("getAllMessage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            MessageQuery query = new MessageQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setTitle(form.getTitle());
            query.setContext(form.getContext());
            query.setType(form.getType());
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
            query.setSendUserName(form.getSendUserName());
            query.setUserName(form.getUserName());
            query.setStatus(form.getStatus());
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultPage<MessageInfo> result = messageFacade.getMessageByCondition(query);
            logger.debug("getAllMessage 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("消息一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 新增消息
     * 
     * @param resp
     * @param form
     * @param bindResult
     */
    //@Security(auth = "MESS_SEND", checkSource = true)
    @RequestMapping(value = "/user/saveMessage.htm", method = RequestMethod.POST)
    public void saveMessage(HttpServletResponse resp, @Valid
    MessageForm form, BindingResult bindResult, HttpServletRequest req) {
        logger.debug("saveMessage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            //Assert.isTrue(SpringSecurityUtil.hasAuth("MESS_SEND"), "没有权限,请联系管理员");
            List<Message> list = new ArrayList<Message>();
            Message message = new Message();
            // 消息插入
            message.setContext(form.getContext());
            message.setTitle(form.getTitle());
            message.setType(form.getType());
            message.setStatus(MessageStatusEnum.UNREAD.getValue());
            message.setCreateTime(new Date());
            message.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            message.setUpdateTime(message.getCreateTime());
            message.setUpdateUserId(message.getCreateUserId());
            message.setCompanyId(SpringSecurityUtil.getIntCompany());
//            if (StringUtils.isNotBlank(form.getAddress())) {
//            }
            message.setAddress(form.getAddress());
            message.setCompanyId(SpringSecurityUtil.getIntCompany());
//            List<StaffBasicInfo> list = new ArrayList<StaffBasicInfo>();
            for (Integer userId : form.getUserIdLst()) {
//                StaffBasicInfo staffInfo = new StaffBasicInfo();
//                staffInfo.setUserId(userId);
                message.setUserId(userId);
                list.add(message);
            }
            ResultEntry<Integer> result = messageFacade.insertBatch(list);
//            MessageHandlerUtil.insertMessage(message, list);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("插入消息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
