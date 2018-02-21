package com.zjzmjr.core.api.weixin;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.weixin.MsgReplyForm;

public interface IMsgReplyFacade {

	Result subscribeSave(MsgReplyForm replyForm);

	Result subscribeRemove(Integer id);

	ResultEntry<MsgReplyForm> subscribeQuery();

}
