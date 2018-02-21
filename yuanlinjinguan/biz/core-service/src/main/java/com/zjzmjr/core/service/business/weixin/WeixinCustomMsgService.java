package com.zjzmjr.core.service.business.weixin;

import com.zjzmjr.core.base.result.ResultEntry;

/**
 * 接收用户发往微信公众号的消息
 * 
 * @author oms
 * @version $Id: WeixinCustomMsgService.java, v 0.1 2017-3-22 下午7:30:44 oms Exp $
 */
public interface WeixinCustomMsgService {

    /**
     * 接收用户发往微信公众号的消息
     * 
     * @param message
     * @return
     */
    ResultEntry<String> receiveCustomTextMessage(String message);

    /**
     * 接收用户发往微信公众号的图片消息
     * 
     * @param message
     * @return
     */
    ResultEntry<String> receiveCustomImageMessage(String message, String[] savePath) throws Exception ;

    /**
     * 接收用户发往微信公众号的视频消息
     * 
     * @param message
     * @return
     */
    ResultEntry<String> receiveCustomVideoMessage(String message, String[] savePath) throws Exception ;
    
    /**
     * 获取用户微信端上传文件时的地理位置
     * 
     * @param message
     * @return
     */
    ResultEntry<String> receiveCustomPositionMessage(String message);
    
}
