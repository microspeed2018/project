package com.zjzmjr.core.api.message;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.message.SendMessage;
import com.zjzmjr.core.model.message.SendMessageInfo;
import com.zjzmjr.core.model.message.SendMessageInfoQuery;
import com.zjzmjr.core.model.message.SendMessageQuery;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: ISendMessageFacade.java, v 0.1 2016-7-21 下午4:23:37 Administrator Exp $
 */
public interface ISendMessageFacade {

    /**
     * 商户端获取群发消息申请列表
     * 
     * @param query
     * @return
     */
    ResultPage<SendMessage> getMessageList(SendMessageQuery query);

    /**
     * 商户端新增群发消息申请
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> insertSendMessage(SendMessage query);

    /**
     * 商户端更新群发消息申请
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> updateSendMessage(SendMessage query);
    /**
     * 获取商户端消息
     * 
     * @param query
     * @return
     */
    ResultPage<SendMessageInfo> getSendMessage(SendMessageInfoQuery query);
    
    /**
     * 审核更新商户消息并发送
     * 
     * @param message
     * @param query
     * @param sendMessage
     * @return
     */
    Result reviewMessage(Message message,SendMessage sendMessage);
    
    /**
     * 通过id获取消息
     * 
     * @param id
     * @return
     */
    ResultRecord<SendMessage> getSendMessageById(Integer id);
    
    

}
