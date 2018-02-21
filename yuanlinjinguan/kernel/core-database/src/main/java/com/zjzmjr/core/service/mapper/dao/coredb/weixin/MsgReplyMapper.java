package com.zjzmjr.core.service.mapper.dao.coredb.weixin;

import java.util.List;

import com.zjzmjr.core.model.weixin.MsgReplyForm;

public interface MsgReplyMapper {

	int insertMsgReply(MsgReplyForm replyForm);

	int deleteMsgReply(Integer id); 
	
	List<MsgReplyForm> queryMsgReply();
	
	List<MsgReplyForm> queryMsgReplyByCondition(MsgReplyForm replyForm);
	
	int updateMsgReply(MsgReplyForm replyForm);
	
}
