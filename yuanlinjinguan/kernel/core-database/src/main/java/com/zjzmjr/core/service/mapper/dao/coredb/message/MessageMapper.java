package com.zjzmjr.core.service.mapper.dao.coredb.message;

import java.util.List;

import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.MessageInfo;
import com.zjzmjr.core.model.message.MessageQuery;

/**
 * 消息模块DAO层
 * 
 * @author lenovo
 * @version $Id: MessageMapper.java, v 0.1 2016-7-13 下午2:27:43 lenovo Exp $
 */
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insertMessage(Message message);

    Message getMessageById(Integer id);

    int updateMessage(Message message);
    
//    List<Message> getMessageByUserId(Integer userId);
    
    Integer getMessagecount(MessageQuery query);    
    
    /**
     * 消息一览
     * 
     * @param query
     * @return
     */
    List<MessageInfo> getMessageByCondition(MessageQuery query);
    
    /**
     * 批量新增
     * 
     * @param messageLst
     * @return
     */
    int insertBatch(List<Message> messageLst);
}
