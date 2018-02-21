package com.zjzmjr.core.service.business.message;


import java.util.List;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.MessageInfo;
import com.zjzmjr.core.model.message.MessageQuery;

/**
 * 消息模块服务层
 * 
 * @author lenovo
 * @version $Id: MessageService.java, v 0.1 2016-7-13 下午2:22:30 lenovo Exp $
 */
public interface MessageService {
  
    /**
     * 获取消息记录
     * 
     * @param userId
     * @return
     */
    ResultPage<MessageInfo> getMessage(Integer userId);
    /**
     * 删除消息
     * 
     * @param id
     * @return
     */
    ResultEntry<Integer> deleteMessageById(Integer id);
    
    /**
     * 获取消息条数
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> getMessageCount(MessageQuery query);
    
    /**
     * 更新消息状态
     * 
     * @param id
     * @return
     */
    ResultEntry<Integer> updateMessage(Message message);
    
    /**
     * 获取消息内容
     * 
     * @param id
     * @return
     */
    ResultEntry<Message> getMessageById(Integer id);
    
    /**
     * 插入默认消息
     * 
     * @param message
     * @return
     */
    ResultEntry<Integer> insertMessage(Message message);
    
//    
//    /**
//     * 群发消息
//     * 
//     * @param message
//     * @return
//     */
//    Result save(Message message);
    
    /**
     * 消息一览
     * 
     * @param query
     * @return
     */
    ResultPage<MessageInfo> getMessageByCondition(MessageQuery query);
    
    /**
     * 批量发送消息
     * 
     * @param messageLst
     * @return
     */
    ResultEntry<Integer> insertBatch(List<Message> messageLst);
}
