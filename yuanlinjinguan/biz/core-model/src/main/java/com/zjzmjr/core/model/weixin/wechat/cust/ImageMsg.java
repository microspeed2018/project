package com.zjzmjr.core.model.weixin.wechat.cust;

import com.zjzmjr.core.model.weixin.wechat.MsgTypeEnum;





public class ImageMsg extends Msg{
	
	private Image image;

	public ImageMsg(){
		super.setMsgtype(MsgTypeEnum.IMAGE_MSG.getMsgEnName());
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ImageMsg [image=" + image + "]";
	}
	
	
}
