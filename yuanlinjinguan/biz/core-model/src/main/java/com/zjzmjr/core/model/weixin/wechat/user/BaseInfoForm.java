package com.zjzmjr.core.model.weixin.wechat.user;


public class BaseInfoForm {
	
	private int total;
	private int count;
	private Data data;
	private String next_openid;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
	@Override
	public String toString() {
		return "UserForm [total=" + total + ", count=" + count + ", data="
				+ data + ", next_openid=" + next_openid + "]";
	}
}
