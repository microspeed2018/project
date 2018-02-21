package com.zjzmjr.core.common.message;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.message.GetuiMessage;


/**
 * 
 * 个推消息推送工具类
 * 
 * @author lenovo
 * @version $Id: GetuiSendMessageUtil.java, v 0.1 2017-12-1 下午1:55:47 lenovo Exp $
 */
public class GetuiSendMessageUtil {

    private static final Logger logger = LoggerFactory.getLogger(GetuiSendMessageUtil.class);

    private static String appId = "xbDIMRuyepA6ou5hNlmfj4";
    
    private static String appKey = "BpjfKuO409A9tSucMRir04";
    
    private static String masterSecret = "vUHds8dML374EPYapQYxV2";
    
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
    
    
    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    @SuppressWarnings("deprecation")
    public static ResultEntry<String> getProjectMaxNo(GetuiMessage getuiMessage){
       ResultEntry<String> result = new ResultEntry<String>();
       try {
           IGtPush push = new IGtPush(url, appKey, masterSecret);
           // 定义"点击链接打开通知模板"，并设置标题、内容、链接
           LinkTemplate template = new LinkTemplate();
           template.setAppId(appId);
           template.setAppkey(appKey);
           template.setTitle("欢迎使用个推!");
           template.setText("这是一条推送消息~");
           template.setUrl("http://baidu.com");
           List<String> appIds = new ArrayList<String>();
           appIds.add(appId);

           // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
           AppMessage message = new AppMessage();
           message.setData(template);
           message.setAppIdList(appIds);
           message.setOffline(true);
           message.setOfflineExpireTime(1000 * 600);
           IPushResult ret = push.pushMessageToApp(message);
           result.setT(ret.getResponse().toString());
       } catch (Exception ex) {
           logger.error("个推测试出错：", ex);
       }
       return result;
   }


  
    /**
     * 发送给个人消息
     * 
     * @param resp
     */
   public static ResultEntry<String> pushMessageToSingle(GetuiMessage getuiMessage) {
       ResultEntry<String> result = new ResultEntry<String>();
       //try {
           //String cid = req.getParameter("cid");
           IGtPush push = new IGtPush(url, appKey, masterSecret);
           NotificationTemplate template = linkTemplateDemo(getuiMessage);
           SingleMessage message = new SingleMessage();
           message.setOffline(true);
           // 离线有效时间，单位为毫秒，可选
           message.setOfflineExpireTime(24 * 3600 * 1000);
           message.setData(template);
           // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
           message.setPushNetWorkType(0);
           Target target = new Target();
           target.setAppId(appId);
           target.setClientId(getuiMessage.getCid());
           //target.setAlias(Alias);
           IPushResult ret = null;
           try {
               ret = push.pushMessageToSingle(message, target);
           } catch (RequestException e) {
               e.printStackTrace();
               ret = push.pushMessageToSingle(message, target, e.getRequestId());
           }
           if (ret != null) {
               result.setT(ret.getResponse().toString());
               result.setSuccess(true);
           } else {
               result.setSuccess(false);
               result.setErrorMsg("服务器响应异常");
           }
           return result;
   }
   
   /**
    * 消息模板定义
    * 
    * @param form
    * @return
    */
   public static NotificationTemplate  linkTemplateDemo(GetuiMessage  form) {
       NotificationTemplate   template = new NotificationTemplate  ();
       // 设置APPID与APPKEY
       template.setAppId(appId);
       template.setAppkey(appKey);
       template.setTransmissionType(2);
       template.setTransmissionContent("吃饭了");
       template.setTitle(form.getTitle());
       template.setText(form.getText());
       template.setLogo(form.getLogo());
       template.setLogoUrl(form.getLogoUrl());
       Style0 style = new Style0();
       // 设置通知栏标题与内容
       style.setTitle(form.getTitle());
       style.setText(form.getText());
       // 配置通知栏图标
       style.setLogo(form.getLogo());
       // 配置通知栏网络图标
       style.setLogoUrl(form.getLogoUrl());
       // 设置通知是否响铃，震动，或者可清除
       style.setRing(true);
       style.setVibrate(true);
       style.setClearable(true);
       template.setStyle(style);
       // 设置打开的网址地址
       //template.setUrl(form.getUrl());
       return template;
   }
   
   /**
    * 推送消息至整个App
    * 
    * @param resp
    * @param req
    * @param form
    */
   public static ResultEntry<String> pushMessageToApp(GetuiMessage getuiMessage) {
       ResultEntry<String> result = new ResultEntry<String>();
       IGtPush push = new IGtPush(url, appKey, masterSecret);

       NotificationTemplate template = linkTemplateDemo(getuiMessage);
       AppMessage message = new AppMessage();
       message.setData(template);

       message.setOffline(true);
       // 离线有效时间，单位为毫秒，可选
       message.setOfflineExpireTime(24 * 1000 * 3600);
       // 推送给App的目标用户需要满足的条件
       AppConditions cdt = new AppConditions();
       List<String> appIdList = new ArrayList<String>();
       appIdList.add(appId);
       message.setAppIdList(appIdList);
       // 手机类型
       //List<String> phoneTypeList = new ArrayList<String>();
       // 省份
       //List<String> provinceList = new ArrayList<String>();
       // 自定义tag
       //List<String> tagList = new ArrayList<String>();

       //cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
       //cdt.addCondition(AppConditions.REGION, provinceList);
       //cdt.addCondition(AppConditions.TAG, tagList);
       message.setConditions(cdt);
       IPushResult ret = push.pushMessageToApp(message);
       if (ret != null) {
           result.setT(ret.getResponse().toString());
           result.setSuccess(true);
       } else {
           result.setSuccess(false);
           result.setErrorMsg("服务器响应异常");
       }
       return result;
   }
   
   /**
    * 推送消息至App多个用户
    * 
    * @param getuiMessage
    * @return
    */
   public static ResultEntry<String> pushMessageToList(GetuiMessage getuiMessage) {
       ResultEntry<String> result = new ResultEntry<String>();
       IGtPush push = new IGtPush(url, appKey, masterSecret);
       // 通知透传模板
       NotificationTemplate template = linkTemplateDemo(getuiMessage);
       ListMessage message = new ListMessage();
       message.setData(template);
       // 设置消息离线，并设置离线时间
       message.setOffline(true);
       // 离线有效时间，单位为毫秒，可选
       message.setOfflineExpireTime(24 * 1000 * 3600);
       // 配置推送目标
       List<Target> targets = new ArrayList<Target>();
       Target target = null;
       if(Util.isNotNull(getuiMessage.getCidList()) && getuiMessage.getCidList().size()>0){
           for(int i=0;i<getuiMessage.getCidList().size();i++){
               target = new Target();
               target.setAppId(appId);
               target.setClientId(getuiMessage.getCidList().get(i));
               targets.add(target);
           }
           
       }
       // taskId用于在推送时去查找对应的message
       String taskId = push.getContentId(message);
       IPushResult ret = push.pushMessageToList(taskId, targets);
       if (ret != null) {
           result.setT(ret.getResponse().toString());
           result.setSuccess(true);
       } else {
           result.setSuccess(false);
           result.setErrorMsg("服务器响应异常");
       }
       return result;
   }
}