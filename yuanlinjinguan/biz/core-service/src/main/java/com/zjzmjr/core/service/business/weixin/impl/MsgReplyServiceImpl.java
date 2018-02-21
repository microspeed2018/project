package com.zjzmjr.core.service.business.weixin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.weixin.MsgReplyForm;
import com.zjzmjr.core.model.weixin.wechat.EventTypeEnum;
import com.zjzmjr.core.service.business.weixin.MsgReplyService;
import com.zjzmjr.core.service.mapper.dao.coredb.weixin.MsgReplyMapper;

@Service("msgReplyService")
public class MsgReplyServiceImpl implements MsgReplyService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgReplyServiceImpl.class);
	
	@Resource(name="msgReplyMapper")
	private MsgReplyMapper  msgReplyMapper;
	
	@Transactional
	@Override
	public Result subscribeSave(MsgReplyForm replyForm) {
	    LOGGER.debug("subscribeSave入参:{}",replyForm);
		Result result = new Result();
		try{
			//查询是否有订阅消息，有则更新，没有则删除
			MsgReplyForm queryForm = new MsgReplyForm();
			queryForm.setEventType(replyForm.getEventType());
			List<MsgReplyForm> list = msgReplyMapper.queryMsgReplyByCondition(queryForm);
			int count = 0;
			if(list!=null&&list.size()>0){
				count=msgReplyMapper.updateMsgReply(replyForm);
			}else{
				count=msgReplyMapper.insertMsgReply(replyForm);
			}
			if(count<=0){
				result.setSuccess(false);
				result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
				result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
			}
		}catch(Exception exception){
			LOGGER.error(exception.getMessage(),exception);
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		LOGGER.debug("subscribeSave出参:{}",result);
		return result;
	}
	
	@Transactional
	@Override
	public Result subscribeRemove(Integer id) {
	    LOGGER.debug("subscribeRemove入参:{}",id);
		Result result = new Result();
		try{
			int count = msgReplyMapper.deleteMsgReply(id);
			if(count<=0){
				result.setSuccess(false);
				result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
				result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
			}
		}catch(Exception exception){
			LOGGER.error(exception.getMessage(),exception);
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		LOGGER.debug("subscribeRemove出参:{}",result);
		return result;
	}
	
	@Transactional
	@Override
	public ResultEntry<MsgReplyForm> subscribeQuery() {
		ResultEntry<MsgReplyForm> result = new ResultEntry<MsgReplyForm>();
		try{
			MsgReplyForm replyForm = new MsgReplyForm();
			replyForm.setEventType(EventTypeEnum.SUBSCRIBE.getEventKey());
			List<MsgReplyForm> list = msgReplyMapper.queryMsgReplyByCondition(replyForm);
			if(list!=null&&list.size()>0){
				MsgReplyForm form = list.get(0);
				result.setT(form);
			}
		}catch(Exception exception){
			LOGGER.error(exception.getMessage(),exception);
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		LOGGER.debug("subscribeQuery出参:{}",result);
		return result;
	}

}
