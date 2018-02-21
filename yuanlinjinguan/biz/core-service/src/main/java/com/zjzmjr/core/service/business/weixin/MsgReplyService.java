package com.zjzmjr.core.service.business.weixin;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.weixin.MsgReplyForm;

public interface MsgReplyService {

	Result subscribeSave(MsgReplyForm replyForm);

	Result subscribeRemove(Integer id);

	ResultEntry<MsgReplyForm> subscribeQuery();

}
