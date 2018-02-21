package com.zjzmjr.admin.web.weixin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.core.api.weixin.IMsgReplyFacade;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.weixin.MsgReplyForm;
import com.zjzmjr.core.model.weixin.wechat.EventTypeEnum;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 消息回复控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/msgReply")
public class MsgReplyController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgReplyController.class);
	
	private static final String defaultPage = "/WEB-INF/pages/weixin/msgReply.jsp";
	
	@Resource(name="msgReplyFacade")
	private IMsgReplyFacade msgReplyFacade;
	
	@RequestMapping(value="/index.htm",method=RequestMethod.GET)
	public String index(){
		return defaultPage;
	}
	
	/**
	 * 保存订阅消息
	 * @param subscribeMsg
	 * @param response
	 * @param modelMap
	 */
	@RequestMapping(value="/subscribeSave.htm",method=RequestMethod.POST)
	public void subscribeSave(@RequestParam("subscribeMsg") String subscribeMsg,HttpServletResponse response,ModelMap modelMap){
        LOGGER.debug("subscribeSave 执行开始  入参:{}", subscribeMsg);	
		if(StringUtils.isNotBlank(subscribeMsg)){
			MsgReplyForm replyForm = new MsgReplyForm();
			replyForm.setContent(subscribeMsg);
			replyForm.setEventType(EventTypeEnum.SUBSCRIBE.getEventKey());
			Result result = msgReplyFacade.subscribeSave(replyForm);
			if(result.isSuccess()){
				putSuccess(modelMap, "");
			}else{
				putError(modelMap, result.getErrorCode(),result.getErrorMsg());
				LOGGER.error(result.getErrorMsg());
			}
		}else{
			putError(modelMap, "被添加自动回复消息不能为空");
			LOGGER.error("被添加自动回复消息不能为空");
		}
		LOGGER.debug("subscribeSave 执行结束  出参:{}", modelMap);
		responseAsJson(modelMap, response);
	}
	
	/**
	 * 移除订阅消息
	 * @param subscribeMsg
	 * @param response
	 * @param modelMap
	 */
	@RequestMapping(value="/subscribeRemove.htm",method=RequestMethod.GET)
	public void subscribeRemove(@RequestParam("id") Integer id,HttpServletResponse response,ModelMap modelMap){	
	    LOGGER.debug("subscribeRemove 执行开始  入参:{}", id);
		if(id!=null){
			Result result = msgReplyFacade.subscribeRemove(id);
			if(result.isSuccess()){
				putSuccess(modelMap, "");
			}else{
				putError(modelMap, result.getErrorCode(),result.getErrorMsg());
				LOGGER.error(result.getErrorMsg());
			}
		}else{
			putError(modelMap, "移除订阅消息失败");
			LOGGER.error("移除订阅消息失败");
		}
		LOGGER.debug("subscribeRemove 执行结束  出参:{}", modelMap);
		responseAsJson(modelMap, response);
	}
	
	/**
	 * 查询订阅消息
	 */
	@RequestMapping(value="/querySubscribe.htm",method=RequestMethod.GET)
	public void subscribeRemove(HttpServletResponse response,ModelMap modelMap){	
	    LOGGER.debug("subscribeRemove 执行开始  入参:{}");
		ResultEntry<MsgReplyForm> result = msgReplyFacade.subscribeQuery();
		if(result.isSuccess()){
			putSuccess(modelMap, "");
			modelMap.put("form", result.getT());
		}else{
			putError(modelMap, result.getErrorCode(),result.getErrorMsg());
			LOGGER.error(result.getErrorMsg());
		}
		LOGGER.debug("subscribeRemove 执行结束  出参:{}", modelMap);
		responseAsJson(modelMap, response);
	}
}
