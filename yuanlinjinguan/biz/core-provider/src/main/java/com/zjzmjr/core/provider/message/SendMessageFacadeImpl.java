package com.zjzmjr.core.provider.message;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.message.ISendMessageFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.SendMessage;
import com.zjzmjr.core.model.message.SendMessageInfo;
import com.zjzmjr.core.model.message.SendMessageInfoQuery;
import com.zjzmjr.core.model.message.SendMessageQuery;
import com.zjzmjr.core.service.business.message.SendMessageService;

/**
 * 商户端消息发送
 * 
 * @author Administrator
 * @version $Id: SendMessageFacadeImpl.java, v 0.1 2016-7-21 下午2:49:44 Administrator Exp $
 */
@Service("sendMessageFacade")
public class SendMessageFacadeImpl implements ISendMessageFacade{
    
    private static final Logger logger = LoggerFactory.getLogger(SendMessageFacadeImpl.class);
    
    @Resource(name = "sendMessageService")
    private SendMessageService sendMessageService;

    /**
     * 
     * @see com.zjzmjr.core.api.merchant.message.ISendMessageFacade#getMessageList(com.zjzmjr.core.model.merchant.message.SendMessageQuery)
     */
    @Override
    public ResultPage<SendMessage> getMessageList(SendMessageQuery query) {
        ResultPage<SendMessage> result = new ResultPage<SendMessage>();
        try{
            result = sendMessageService.getMessageList(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.merchant.message.ISendMessageFacade#insertSendMessage(com.zjzmjr.core.model.merchant.message.SendMessage)
     */
    @Override
    public ResultEntry<Integer> insertSendMessage(SendMessage query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try{
            result = sendMessageService.insertSendMessage(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.merchant.message.ISendMessageFacade#updateSendMessage(com.zjzmjr.core.model.merchant.message.SendMessage)
     */
    @Override
    public ResultEntry<Integer> updateSendMessage(SendMessage query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try{
            result = sendMessageService.updateSendMessage(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.merchant.message.ISendMessageFacade#getSendMessage(com.zjzmjr.core.model.merchant.message.SendMessageInfoQuery)
     */
    @Override
    public ResultPage<SendMessageInfo> getSendMessage(SendMessageInfoQuery query) {
        ResultPage<SendMessageInfo> result=new ResultPage<SendMessageInfo>();
        try {
            result=sendMessageService.getSendMessage(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.merchant.message.ISendMessageFacade#reviewMessage(com.zjzmjr.core.model.message.Message, com.zjzmjr.core.model.user.UserQuery, com.zjzmjr.core.model.merchant.message.SendMessage)
     */
    @Override
    public Result reviewMessage(Message message, SendMessage sendMessage) {
        Result result=new Result();
        try {
             result=sendMessageService.reviewMessage(message, sendMessage);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.merchant.message.ISendMessageFacade#getSendMessageById(java.lang.Integer)
     */
    @Override
    public ResultRecord<SendMessage> getSendMessageById(Integer id) {
        ResultRecord<SendMessage> result=new ResultRecord<SendMessage>();
        try {
            result=sendMessageService.getSendMessageById(id);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    
}
