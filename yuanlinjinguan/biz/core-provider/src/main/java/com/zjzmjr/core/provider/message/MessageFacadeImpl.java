package com.zjzmjr.core.provider.message;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.message.IMessageFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.MessageInfo;
import com.zjzmjr.core.model.message.MessageQuery;
import com.zjzmjr.core.service.business.message.MessageService;


@Service("messageFacade")
public class MessageFacadeImpl implements IMessageFacade {

    private static final Logger logger = LoggerFactory.getLogger(MessageFacadeImpl.class);
    
    @Resource(name="messageService")
    private MessageService messageService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.message.IMessageFacade#getMessage(java.lang.Integer)
     */
    @Override
    public ResultPage<MessageInfo> getMessage(Integer userId) {
       ResultPage<MessageInfo> result=new ResultPage<MessageInfo>();
       try {
        result=messageService.getMessage(userId);
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
     * @see com.zjzmjr.core.api.message.IMessageFacade#deleteMessageById(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteMessageById(Integer id) {
        ResultEntry<Integer> result=new ResultEntry<Integer>();
        try {
            result=messageService.deleteMessageById(id);
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
     * @see com.zjzmjr.core.api.message.IMessageFacade#getMessageCount(com.zjzmjr.core.model.message.MessageQuery)
     */
    @Override
    public ResultEntry<Integer> getMessageCount(MessageQuery query) {
       ResultEntry<Integer> result=new ResultEntry<Integer>();
       try {
        result=messageService.getMessageCount(query);
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
     * @see com.zjzmjr.core.api.message.IMessageFacade#updateMessage(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> updateMessage(Message message) {
        ResultEntry<Integer> result=new ResultEntry<Integer>();
        try {
          result=messageService.updateMessage(message);
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
     * @see com.zjzmjr.core.api.message.IMessageFacade#getMessageById(java.lang.Integer)
     */
    @Override
    public ResultEntry<Message> getMessageById(Integer id) {
        ResultEntry<Message> result=new ResultEntry<Message>();
        try {
            result=messageService.getMessageById(id);
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
     * @see com.zjzmjr.core.api.message.IMessageFacade#insertMessage()
     */
    @Override
    public ResultEntry<Integer> insertMessage(Message message) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = messageService.insertMessage(message);
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
    * @see com.zjzmjr.core.api.message.IMessageFacade#save(com.zjzmjr.core.model.message.Message)
    */
    @Override
    public Result save(Message message) {
     Result result=new Result();
     try {
//       result=messageService.save(message); 
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
     * @see com.zjzmjr.core.api.message.IMessageFacade#getMessageByCondition(com.zjzmjr.core.model.message.MessageQuery)
     */
    @Override
    public ResultPage<MessageInfo> getMessageByCondition(MessageQuery query) {
        ResultPage<MessageInfo> result = new ResultPage<MessageInfo>();
        try {
            result = messageService.getMessageByCondition(query);
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
     * @see com.zjzmjr.core.api.message.IMessageFacade#insertBatch(java.util.List)
     */
    @Override
    public ResultEntry<Integer> insertBatch(List<Message> messageLst) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = messageService.insertBatch(messageLst);
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
