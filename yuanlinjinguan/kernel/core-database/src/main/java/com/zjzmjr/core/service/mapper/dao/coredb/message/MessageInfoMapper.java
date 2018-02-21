package com.zjzmjr.core.service.mapper.dao.coredb.message;

import java.util.List;

import com.zjzmjr.core.model.message.MessageInfo;
import com.zjzmjr.core.model.message.MessageInfoQuery;

/**
 * 消息关联DAO
 * 
 * @author lenovo
 * @version $Id: MessageInfoMapper.java, v 0.1 2016-7-13 下午2:29:25 lenovo Exp $
 */
public interface MessageInfoMapper {

    int getAllMessageCount(MessageInfoQuery query);
    
    List<MessageInfo> getAllMessage(MessageInfoQuery query);
}
