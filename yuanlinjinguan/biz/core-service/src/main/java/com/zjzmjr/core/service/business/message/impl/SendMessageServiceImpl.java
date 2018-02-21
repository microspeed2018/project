package com.zjzmjr.core.service.business.message.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.SendMessage;
import com.zjzmjr.core.model.message.SendMessageInfo;
import com.zjzmjr.core.model.message.SendMessageInfoQuery;
import com.zjzmjr.core.model.message.SendMessageQuery;
import com.zjzmjr.core.service.business.message.MessageService;
import com.zjzmjr.core.service.business.message.SendMessageService;
import com.zjzmjr.core.service.mapper.dao.coredb.message.SendMessageDao;
import com.zjzmjr.core.service.mapper.dao.coredb.message.SendMessageInfoDao;

/**
 * 商户端群发消息服务层
 * 
 * @author Administrator
 * @version $Id: SendMessageServiceImpl.java, v 0.1 2016-7-21 上午11:21:44 Administrator Exp $
 */
@Service("sendMessageService")
public class SendMessageServiceImpl implements SendMessageService{
    
    private static final Logger logger = LoggerFactory.getLogger(SendMessageServiceImpl.class);
    
    @Resource(name = "sendMessageDao")
    private SendMessageDao sendMessageDao;

    @Resource(name = "messageService")
    private MessageService messageService;
    
    @Resource(name = "sendMessageInfoDao")
    private SendMessageInfoDao sendMessageInfoDao;
    /**
     * 
     * @see com.zjzmjr.core.service.business.merchant.message.SendMessageService#getMessageList(com.zjzmjr.core.model.merchant.message.SendMessageQuery)
     */
    @Override
    public ResultPage<SendMessage> getMessageList(SendMessageQuery query) {
        logger.debug("getMessageList入参:{}",query);
        ResultPage<SendMessage> result = new ResultPage<SendMessage>();
        int total = sendMessageDao.getMessageCount();
        if(total > 0){
            List<SendMessage> data= sendMessageDao.getMessageList(query);
            result.setSuccess(true);
            result.setList(data);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getMessageList出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.merchant.message.SendMessageService#insertSendMessage(com.zjzmjr.core.model.merchant.message.SendMessage)
     */
    @Override
    @Transactional
    public ResultEntry<Integer> insertSendMessage(SendMessage query) {
        logger.debug("insertSendMessage入参:{}",query);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        int data= sendMessageDao.insertSendMessage(query);
        if(data > 0){
            result.setSuccess(true);
            result.setT(data);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        logger.debug("insertSendMessage出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.merchant.message.SendMessageService#updateSendMessage(com.zjzmjr.core.model.merchant.message.SendMessage)
     */
    @Override
    public ResultEntry<Integer> updateSendMessage(SendMessage query) {
        logger.debug("updateSendMessage入参:{}",query);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        int data= sendMessageDao.updateSendMessage(query);
        if(data > 0){
            result.setSuccess(true);
            result.setT(data);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        logger.debug("updateSendMessage出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.merchant.message.SendMessageService#getSendMessage(com.zjzmjr.core.model.merchant.message.SendMessageInfoQuery)
     */
    @Override
    public ResultPage<SendMessageInfo> getSendMessage(SendMessageInfoQuery query) {
        logger.debug("getSendMessage入参:{}",query);
        ResultPage<SendMessageInfo> result=new ResultPage<SendMessageInfo>();
        if(null==query||null==query.getPageBean()){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),query.toString());
            
        } logger.info("获取商户端消息：{}", query.toString());
        int total=sendMessageInfoDao.getAllSendMessageCount(query);
        if(total>0){
            List<SendMessageInfo>list=sendMessageInfoDao.getAllSendMessage(query);
            result.setList(list);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getSendMessage出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.merchant.message.SendMessageService#reviewMessage(com.zjzmjr.core.model.message.Message, com.zjzmjr.core.model.user.UserQuery, com.zjzmjr.core.model.merchant.message.SendMessage)
     */
    @Override
    @Transactional
    public Result reviewMessage(Message message,SendMessage sendMessage) {
        logger.debug("getSendMessage入参:message:{},sendMessage:{}",message,sendMessage);
        Result result=new Result();
        if(Util.isNull(sendMessage)||Util.isNull(sendMessage.getId())||Util.isNull(sendMessage.getStatus())||Util.isNull(message)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),sendMessage.toString());  
        }
        logger.info("获取商户端消息：{}", sendMessage.toString());
        int total=sendMessageDao.updateSendMessage(sendMessage);
        if(total>0){
            Thread t = new Thread(new MessageThread(message));
            t.start();
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        logger.debug("reviewMessage出参:{}",result);
        return result;
    }

    /**
     * 发送消息的线程
     * 
     * @author lenovo
     * @version $Id: SendMessageServiceImpl.java, v 0.1 2016-7-21 下午8:19:36 lenovo Exp $
     */
    private class MessageThread implements Runnable{
        private Message message;
        
        public MessageThread(Message message){
            this.message = message;
          
        }

        @Override
        public void run() {
//            messageService.save(message);
        }
        
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.merchant.message.SendMessageService#getSendMessageById(java.lang.Integer)
     */
    @Override
    public ResultRecord<SendMessage> getSendMessageById(Integer id) {
        logger.debug("getSendMessageById入参:{}",id);
        ResultRecord<SendMessage> result=new ResultRecord<SendMessage>();
        if(Util.isNull(id)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),id.toString()); 
        }
        logger.info("通过id获取商户端消息：{}", id);
        List<SendMessage> list=sendMessageDao.getSendMessageById(id);
        if(list==null||list.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false); 
        }else{
            result.setSuccess(true);
            result.setList(list);
        }
        logger.debug("getSendMessageById出参:{}",result);
        return result;
    }

}
