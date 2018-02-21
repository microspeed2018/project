package com.zjzmjr.core.api.message;

import java.util.List;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.MessageInfo;
import com.zjzmjr.core.model.message.MessageQuery;


public interface IMessageFacade {
   
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
     * 获取未读消息条数
     * 
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
     * @param message 
     */
    ResultEntry<Integer> insertMessage(Message message);
    
    /**
     * 群发消息
     * 
     * @param message
     * @return
     */
    Result save(Message message);
    
    /**
     * 条件查询消息
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
