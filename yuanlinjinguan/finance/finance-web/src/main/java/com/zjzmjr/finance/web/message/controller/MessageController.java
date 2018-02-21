package com.zjzmjr.finance.web.message.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.zjzmjr.core.api.message.IMessageFacade;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.MessageInfo;
import com.zjzmjr.core.model.message.MessageQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 消息模块控制层
 * 
 * @author lenovo
 * @version $Id: MessageController.java, v 0.1 2016-7-13 上午11:12:13 lenovo Exp $
 */
@Controller
@RequestMapping(value="/message")
public class MessageController extends BaseController{
   
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    
    @Resource(name="messageFacade")
    private IMessageFacade messageFacade;
    
    /**
     * 获取消息记录
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/user/getMessage.htm", method = RequestMethod.POST)
    public void getMessage(ModelMap model, HttpServletResponse resp,HttpServletRequest req){
        try {
            MessageQuery query = new MessageQuery();
            query.setUserId(SpringSecurityUtil.getIntPrincipal());
            String type = req.getParameter("type");
            if(!StringUtils.isBlank(type)){
                query.setType(Integer.parseInt(type));  
            }           
            ResultPage<MessageInfo> result = messageFacade.getMessageByCondition(query);
            if (result.isSuccess()) {
                List<MessageInfo> message = result.getList();
//                for(int i=0;i<message.size();i++){
//                    //根据消息类型拼接消息标题
//                    if(MessageTypeEnum.NOTICE.getValue()==message.get(i).getType()){
//                        message.get(i).setTitle(MessageTypeEnum.NOTICE.getMessage()+message.get(i).getTitle());
//                    }
//                    if(MessageTypeEnum.REMIND.getValue()==message.get(i).getType()){
//                        message.get(i).setTitle(MessageTypeEnum.REMIND.getMessage()+message.get(i).getTitle());
//                    }
//                }
                model.put("data", message);
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获得消息记录出错：", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("getMessage出参:{}",model);
        responseAsJson(model, resp);
    }
   
    /**
     * 删除消息
     * 
     * @param model
     * @param resp
     * @param id
     */
    @RequestMapping(value = "/user/{id}/deleteMessage.htm", method = RequestMethod.POST)
    public void deleteMessage(ModelMap model, HttpServletResponse resp,@PathVariable Integer id){
        logger.debug("deleteMessage入参:{}",id);
        try {
            ResultEntry<Integer> result=messageFacade.deleteMessageById(id);//根据消息id删除消息
            if(result.isSuccess()){
                putSuccess(model, ""); 
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg()); 
            }
        } catch (Exception ex) {
            logger.error("删除消息出错：", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("deleteMessage出参:{}",model);
        responseAsJson(model, resp);
       
    }
    
    /**
     * 获取未读消息数量
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/user/getMessageCount.htm", method = RequestMethod.POST)
    public void getMessageCount(ModelMap model, HttpServletResponse resp){
        MessageQuery query=new MessageQuery();
       try {
        query.setUserId(SpringSecurityUtil.getIntPrincipal());//获取用户id
        query.setStatus(MessageStatusEnum.UNREAD.getValue());//获取消息状态
        ResultEntry<Integer> result=messageFacade.getMessageCount(query);//获取未读消息条数
        if(result.isSuccess()){
            model.put("num", result.getT());
            putSuccess(model, ""); 
        }else{
            putError(model, result.getErrorCode(), result.getErrorMsg()); 
        }
    } catch (Exception ex) {
        logger.error("获得未读消息条数出错：", ex);
        putError(model, ex.getMessage());
    }
       logger.debug("getMessageCount出参:{}",model);
    responseAsJson(model, resp);
    }
    
    /**
     * 更新消息状态
     * 
     * @param model
     * @param resp
     * @param id
     */
    @RequestMapping(value = "/user/updateMessage.htm", method = RequestMethod.POST)
    public void updateMessage(ModelMap model, HttpServletRequest req, HttpServletResponse resp){
        try {
            
            String id=req.getParameter("id");//传入消息id
            ResultEntry<Message> result=messageFacade.getMessageById(Integer.parseInt(id));
            if(MessageStatusEnum.UNREAD.getValue()==result.getT().getStatus()){
                Message message=result.getT();
                message.setStatus(MessageStatusEnum.READ.getValue());//更新消息状态
                message.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                message.setUpdateTime(new Date());
                ResultEntry<Integer> resultUpdate=messageFacade.updateMessage(message);
                if(resultUpdate.isSuccess()){
                    putSuccess(model, "");   
                }else{
                    putError(model, result.getErrorCode(), result.getErrorMsg());  
                }
                 
            }else{
                putError(model, "消息已读"); 
            }
         
        } catch (Exception ex) {
            logger.error("更新消息状态出错：", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("updateMessage出参:{}",model);
        responseAsJson(model, resp);
    }
    
    
}
