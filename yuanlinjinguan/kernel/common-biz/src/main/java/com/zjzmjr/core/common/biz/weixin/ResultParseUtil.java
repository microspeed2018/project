package com.zjzmjr.core.common.biz.weixin;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信响应消息解析类
 * @author Administrator
 *
 */
public class ResultParseUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Logger logger=LoggerFactory.getLogger(ResultParseUtil.class);
	
	/**
	 * 调用API是否成功
	 */
	private boolean success = true;
	
	/**
	 * 错误码
	 */
	private Integer errcode;
	
	/**
	 * 错误消息
	 */
	private String errmsg;
	
	/**
	 * 消息ID
	 */
	private String msgid;
	
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	public String getMsgid() {
		return msgid;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	/**
	 * 解析响应内容
	 * @return
	 */
	public ResultParseUtil parseResult(){
		if(errcode!=null){
			if(errcode==ResultEnum.REQUEST_SUCCESS.getErrcode()){
				this.setErrcode(ResultEnum.REQUEST_SUCCESS.getErrcode());
				this.setErrmsg(ResultEnum.REQUEST_SUCCESS.getErrmsg());
				this.setSuccess(true);
				return this;
			}else if(errcode==ResultEnum.SYSTEM_BUSY.getErrcode()){
				this.setErrcode(ResultEnum.SYSTEM_BUSY.getErrcode());
				this.setErrmsg(ResultEnum.SYSTEM_BUSY.getErrmsg());
				this.setSuccess(false);
				return this;
			}else{
				this.setSuccess(false);
				this.setErrmsg(ResultEnum.REQUEST_FAIL.getErrmsg()+": "+this.toString());
				this.setErrcode(ResultEnum.REQUEST_FAIL.getErrcode());
				logger.error(this.toString());
				return this;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "ResultParseUtil [success=" + success + ",errcode=" + errcode + ", errmsg=" + errmsg
				+ "]";
	}
	
}
