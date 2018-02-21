package com.zjzmjr.core.provider.weixin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.weixin.IWeixinMenuFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.common.biz.weixin.HttpsRequestUtil;
import com.zjzmjr.core.common.biz.weixin.JSONProcesserUtil;
import com.zjzmjr.core.common.biz.weixin.ResultParseUtil;
import com.zjzmjr.core.common.biz.weixin.XMLUtil;
import com.zjzmjr.core.model.weixin.MenuForm;
import com.zjzmjr.core.model.weixin.wechat.menu.CommonMsgPush;
import com.zjzmjr.core.model.weixin.wechat.menu.EventPush;
import com.zjzmjr.core.model.weixin.wechat.menu.Menu;
import com.zjzmjr.core.model.weixin.wechat.menu.MsgReply;
import com.zjzmjr.core.service.business.weixin.WeixinCustomMsgService;
import com.zjzmjr.core.service.business.weixin.WeixinMenuService;

@Service("weixinMenuFacade")
public class WeixinMenuFacadeImpl implements IWeixinMenuFacade{
	
	private Logger logger=LoggerFactory.getLogger(WeixinMenuFacadeImpl.class);
	
	@Resource(name="weixinMenuService")
	private WeixinMenuService weixinMenuService;
	
	@Resource(name = "weixinCustomMsgService")
	private WeixinCustomMsgService weixinCustomMsgService;
	
	private static final String MENU_CREATE_URL= "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	@Override
	public ResultEntry<List<MenuForm>> getMenuList() {
		ResultEntry<List<MenuForm>> result=new ResultEntry<List<MenuForm>>();
		try{
			result=weixinMenuService.getMenuList();
		}catch(Exception ex){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		return result;
	}

	@Override
	public ResultEntry<Integer> updateMenu(MenuForm menu) {
		ResultEntry<Integer> result=new ResultEntry<Integer>();
		try{
			result=weixinMenuService.updateMenu(menu);
		}catch(Exception ex){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		return result;
	}

	@Override
	public ResultEntry<Integer> deleteMenu(Integer pid,Integer id) {
		ResultEntry<Integer> result=new ResultEntry<Integer>();
		try{
			result=weixinMenuService.deleteMenu(pid,id);
		}catch(Exception ex){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		return result;
	}

	@Override
	public ResultEntry<Integer> addMenu(MenuForm menu) {
		ResultEntry<Integer> result=new ResultEntry<Integer>();
		try{
			result=weixinMenuService.addMenu(menu);
		}catch(Exception ex){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		return result;
	}
	
	/**
	 * 添加微信菜单
	 */
	@Override
	public ResultEntry<Integer> addWeixinMenu(Menu menu, String access_token) {
		ResultEntry<Integer> result=new ResultEntry<Integer>();
		try{
			String url=MENU_CREATE_URL.replace("ACCESS_TOKEN", access_token).replace("\"", "");
			String menuStr=JSONProcesserUtil.parseObj(menu);
			String data=HttpsRequestUtil.request(url, "POST",menuStr);
			ResultParseUtil parseUtil=JSONProcesserUtil.parseStr(data, ResultParseUtil.class);
			parseUtil=parseUtil.parseResult();
			result.setSuccess(parseUtil.isSuccess());
			result.setErrorCode(String.valueOf(parseUtil.getErrcode()));
			result.setErrorMsg(parseUtil.getErrmsg());
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 接收微信事件推送
	 */
	@Override
	public String eventHandler(String xmlStr) {
		//事件推送
		if(xmlStr.contains("Event")){
			EventPush eventPush=XMLUtil.convertToObj(EventPush.class, xmlStr);
			//处理菜单单击事件
			if("event".equals(eventPush.getMsgType())&&"CLICK".equals(eventPush.getEvent())){
				MsgReply msgReply=new MsgReply();
				ResultEntry<List<MenuForm>> result=getMenuList();
				Map<String, String> paramMap=new HashMap<String, String>();
				if(result.isSuccess()){
					List<MenuForm> list=result.getT();
					for(MenuForm form:list){
						String type=form.getType();
						if(type.equals("click")){
							paramMap.put(form.getKey(), form.getContent());
						}else if(type.equals("other")){
							for(MenuForm subForm:form.getMid()){
								if(subForm.getType().equals("click")){
									paramMap.put(subForm.getKey(), subForm.getContent());
								}
							}
						}
					}
				}
				msgReply.setCreateTime(System.currentTimeMillis());
				msgReply.setFromUserName(eventPush.getToUserName());
				msgReply.setToUserName(eventPush.getFromUserName());
				msgReply.setMsgType("text");
				String enevtKey=eventPush.getEventKey();
				for(Map.Entry<String, String> entry:paramMap.entrySet()){
					if(entry.getKey().equals(enevtKey)){
						msgReply.setContent(entry.getValue());
						break;
					}
				}
				return XMLUtil.convertToXML(msgReply);
			}
			//用户未关注时，扫描带参数二维码事件|关注事件
			if("event".equals(eventPush.getMsgType())&&"subscribe".equals(eventPush.getEvent())){
//				//用户未关注时，扫描带参数二维码事件
//				if(StringUtils.isNotBlank(eventPush.getTicket())){
//					String openid=eventPush.getFromUserName();
//					//参数二维码场景id
//					String key=eventPush.getEventKey();
//				//关注事件
//				}else{
//					
//				}
				//扫描带参数二维码和关注事件推送同样的消息
				MsgReply msgReply=new MsgReply();
				msgReply.setFromUserName(eventPush.getToUserName());
				msgReply.setToUserName(eventPush.getFromUserName());
				msgReply.setCreateTime(System.currentTimeMillis());
				msgReply.setContent("你好，欢迎关注中茗金融服务。");
				msgReply.setMsgType("text");
				return XMLUtil.convertToXML(msgReply);
			}
			//用户已关注时，扫描带参数二维码事件
			if("event".equals(eventPush.getMsgType())&&"SCAN".equals(eventPush.getEvent())){
//				String openid=eventPush.getFromUserName();
//				String sceneId=eventPush.getEventKey();
				return "";
			}
			//取消关注事件
			if("event".equals(eventPush.getMsgType())&&"unsubscribe".equals(eventPush.getEvent())){
				return "";
			}
            // 获取用户的地理位置
            if("event".equals(eventPush.getMsgType())&&"LOCATION".equals(eventPush.getEvent())){
                ResultEntry<String> result = weixinCustomMsgService.receiveCustomPositionMessage(xmlStr);
                return result.getT();
            }
		}
		//消息推送-多客服
		CommonMsgPush msgPush = XMLUtil.convertToObj(CommonMsgPush.class, xmlStr);
		MsgReply msgReply = new MsgReply();
		msgReply.setFromUserName(msgPush.getToUserName());
		msgReply.setToUserName(msgPush.getFromUserName());
		msgReply.setCreateTime(System.currentTimeMillis());
		msgReply.setMsgType("transfer_customer_service");
		String msg= XMLUtil.convertToXML(msgReply);
		return msg;
	}
	
	/**
	 * 
	 * @see com.zjzmjr.loan.api.weixin.IWeixinMenuFacade#receiveCustomMessage(java.lang.String)
	 */
	@Override
    public String receiveCustomMessage(String message, String[] savePath) {
        ResultEntry<String> result = new ResultEntry<String>();
        try {
            if (message.contains("CDATA[image]")) {
                result = weixinCustomMsgService.receiveCustomImageMessage(message, savePath);
            } else if (message.contains("CDATA[text]")) {
                result = weixinCustomMsgService.receiveCustomTextMessage(message);
            } else if (message.contains("CDATA[video]")) {
                result = weixinCustomMsgService.receiveCustomVideoMessage(message, savePath);
            }
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
        }
        return result.getT();
    }

}
