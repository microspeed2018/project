package com.zjzmjr.core.service.mapper.dao.coredb.message;

import java.util.List;

import com.zjzmjr.core.model.message.SendMessageInfo;
import com.zjzmjr.core.model.message.SendMessageInfoQuery;


public interface SendMessageInfoDao {

    int getAllSendMessageCount (SendMessageInfoQuery query);
    
    List<SendMessageInfo> getAllSendMessage (SendMessageInfoQuery query);
}
