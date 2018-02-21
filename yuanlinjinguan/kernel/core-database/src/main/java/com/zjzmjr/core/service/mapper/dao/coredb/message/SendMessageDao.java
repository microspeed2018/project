package com.zjzmjr.core.service.mapper.dao.coredb.message;

import java.util.List;

import com.zjzmjr.core.model.message.SendMessage;
import com.zjzmjr.core.model.message.SendMessageQuery;

/**
 * 商户端群发消息DAO层
 * 
 * @author Administrator
 * @version $Id: SendMessageDao.java, v 0.1 2016-7-21 上午11:23:30 Administrator Exp $
 */
public interface SendMessageDao {

    /**
     * 获取商户端群发消息申请列表
     * 
     * @param query
     * @return
     */
    List<SendMessage> getMessageList(SendMessageQuery query);

    /**
     * 商户端新增群发消息申请
     * 
     * @param query
     * @return
     */
    int insertSendMessage(SendMessage query);

    /**
     * 分页查询统计
     * 
     */
    int getMessageCount();
    
    
    /**
     * 通过id获取消息
     * 
     * @param id
     * @return
     */
    List<SendMessage> getSendMessageById(Integer id);

    /**
     * 商户端更新群发消息申请
     * 
     * @param query
     * @return
     */
    int updateSendMessage(SendMessage query);

}
