package com.zjzmjr.core.provider.weixin;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.weixin.IMsgReplyFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.weixin.MsgReplyForm;
import com.zjzmjr.core.service.business.weixin.MsgReplyService;

@Service("msgReplyFacade")
public class MsgReplyFacadeImpl implements IMsgReplyFacade{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgReplyFacadeImpl.class);
	
	@Resource(name="msgReplyService")
	private MsgReplyService msgReplyService;
	
	@Override
	public Result subscribeSave(MsgReplyForm replyForm) {
		Result result = new Result();
		try{
			result = msgReplyService.subscribeSave(replyForm);
		}catch(Exception exception){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
			LOGGER.error(exception.getMessage(),exception);
		}
		return result;
	}

	@Override
	public Result subscribeRemove(Integer id) {
		Result result = new Result();
		try{
			result = msgReplyService.subscribeRemove(id);
		}catch(Exception exception){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
			LOGGER.error(exception.getMessage(),exception);
		}
		return result;
	}

	@Override
	public ResultEntry<MsgReplyForm> subscribeQuery() {
		ResultEntry<MsgReplyForm> result = new ResultEntry<MsgReplyForm>();
		try{
			result = msgReplyService.subscribeQuery();
		}catch(Exception exception){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
			LOGGER.error(exception.getMessage(),exception);
		}
		return result;
	}

}
