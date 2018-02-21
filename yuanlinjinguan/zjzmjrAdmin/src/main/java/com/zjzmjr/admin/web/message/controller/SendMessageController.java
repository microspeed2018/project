package com.zjzmjr.admin.web.message.controller;

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

import com.zjzmjr.admin.web.message.form.SendMessageForm;
import com.zjzmjr.core.api.message.ISendMessageFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.enums.message.MessageTypeEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.SendMessage;
import com.zjzmjr.core.model.message.SendMessageInfo;
import com.zjzmjr.core.model.message.SendMessageInfoQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;



@Controller
@RequestMapping("/sendMessage")
public class SendMessageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(SendMessageController.class);
    
    private final static String index = "/WEB-INF/pages/message/send_message.jsp";
    
    @RequestMapping("/index.htm")
    public String index(ModelMap model) {

        return index;
    }
    
    @Resource(name="sendMessageFacade")
    private ISendMessageFacade sendMessageFacade;
    
    @RequestMapping(value = "/user/getSendMessage.htm", method = RequestMethod.POST)
    public void  getSendMessage( HttpServletResponse resp, SendMessageForm form){
        logger.debug("getSendMessage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            SendMessageInfoQuery query=new SendMessageInfoQuery();
            query.setStatus(form.getStatus());
            query.setTitle(form.getTitle());
            if (StringUtils.isBlank(form.getCreateTime())&&StringUtils.isBlank(form.getLastTime())) {
                query.setCreateTime(null);
                query.setLastTime(null);
            } else {
                query.setCreateTime(DateUtil.parse(form.getCreateTime(), DateUtil.YY_MM_DD));
                query.setLastTime(DateUtil.parse(form.getLastTime(), DateUtil.YY_MM_DD));
            }
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultPage<SendMessageInfo> result=sendMessageFacade.getSendMessage(query);
            logger.debug("getSendMessage 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                List<SendMessageInfo> sendMessageInfo = result.getList();
                for(int i=0;i<sendMessageInfo.size();i++){
                    if(MessageTypeEnum.NOTICE.getValue()==sendMessageInfo.get(i).getType()){
                        sendMessageInfo.get(i).setTitle(MessageTypeEnum.NOTICE.getMessage()+sendMessageInfo.get(i).getTitle());
                    }
                    if(MessageTypeEnum.REMIND.getValue()==sendMessageInfo.get(i).getType()){
                        sendMessageInfo.get(i).setTitle(MessageTypeEnum.REMIND.getMessage()+sendMessageInfo.get(i).getTitle());
                    }
                }
                model.put("rows", sendMessageInfo);
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            }else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("消息一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
   
    @RequestMapping(value = "/user/reviewMessage.htm", method = RequestMethod.POST)
    public void reviewMessage(HttpServletResponse resp, @Valid SendMessageForm form, BindingResult bindResult,HttpServletRequest req){
        logger.debug("reviewMessage 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            if(!resolveBindingError(form, bindResult, model, false)){
                Message message=new Message();
                SendMessage sendMessage=new SendMessage();
                sendMessage.setId(form.getId());
                sendMessage.setUpdateTime(new Date());
                sendMessage.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
//                if(ApplyStatusEnum.SUCCESS.getValue().equals(form.getStatus())){
                if(SpringSecurityUtil.getIntPrincipal().equals(form.getStatus())){
//                    sendMessage.setStatus(ApplyStatusEnum.SUCCESS.getValue());
                    ResultRecord<SendMessage> sendResult=sendMessageFacade.getSendMessageById(form.getId());
                    List<SendMessage> list=sendResult.getList();
                    message.setContext(list.get(0).getContext());
                    message.setTitle(list.get(0).getTitle());
                    message.setType(list.get(0).getType());
                    message.setStatus(MessageStatusEnum.UNREAD.getValue());
                    message.setCreateTime(new Date());
                    message.setCreateUserId(list.get(0).getMerchantId());
//                    message.setMerchantId(list.get(0).getMerchantId());
                    //新增管理员事物
                    AdminBusiness adminBusiness=new AdminBusiness();
//                    adminBusiness.setBusinessType(AdminBusinessTypeEnum.REVIEW_MESSAGE.getValue());
                    String message2=StringUtils.isEmpty(form.getTitle())?"":"消息标题："+form.getTitle();
                    adminBusiness.setExtend1(message2);
                    message2=StringUtils.isEmpty(form.getMerchantName())?"":"商户名称："+form.getMerchantName();
                    adminBusiness.setExtend2(message2);
                    ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
                    Result result=sendMessageFacade.reviewMessage(message,sendMessage);
                    logger.debug("reviewMessage 执行结束  出参:{}", result);
                    if(result.isSuccess()){
                        putSuccess(model, "");
                        adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    }else{
                        putError(model, result.getErrorCode(), result.getErrorMsg());
                        adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                        adminBusiness.setComment("错误消息："+result.getErrorMsg());
                    }
                    //更新管理员事物
                    adminBusiness.setId(val.getT().getId());
                    AdminTransactionUtil.updateAdminTransaction(adminBusiness);
                }else{
//                    sendMessage.setStatus(ApplyStatusEnum.FAIL.getValue());
                    //新增管理员事物
                    AdminBusiness adminBusiness=new AdminBusiness();
//                    adminBusiness.setBusinessType(AdminBusinessTypeEnum.REVIEW_MESSAGE.getValue());
                    String message2=StringUtils.isEmpty(form.getTitle())?"":"消息标题："+form.getTitle();
                    adminBusiness.setExtend1(message2);
                    message2=StringUtils.isEmpty(form.getMerchantName())?"":"商户名称："+form.getMerchantName();
                    adminBusiness.setExtend2(message2);
                    ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
                    ResultEntry<Integer> result=sendMessageFacade.updateSendMessage(sendMessage);
                    logger.debug("reviewMessage 执行结束  出参:{}", result);
                    if(result.isSuccess()){
                        putSuccess(model, "");
                        adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    }else{
                        putError(model, result.getErrorCode(), result.getErrorMsg()); 
                        adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                        adminBusiness.setComment("错误消息："+result.getErrorMsg());
                    }
                    //更新管理员事物
                    adminBusiness.setId(val.getT().getId());
                    AdminTransactionUtil.updateAdminTransaction(adminBusiness);
                } 
               
            }
        } catch (Exception ex) {
            logger.error("审核商户端消息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
