package com.zjzmjr.admin.web.weixin.form;

import java.io.Serializable;
import java.util.List;

public class TreeForm implements Serializable {

	private static final long serialVersionUID = -6020422390618407703L;
	/**
	 * id：节点ID，对加载远程数据很重要。 text：显示节点文本。 state：节点状态，'open' 或
	 * 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。 checked：表示该节点是否被选中。
	 * attributes: 被添加到节点的自定义属性。 children: 一个节点数组声明了若干节点。
	 */
	private Integer id;
	private String text;
	private String state;
	private boolean checked;
	private Attributes attributes;
	private List<TreeForm> children;
	private String iconCls;

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getIconCls() {
		return iconCls;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public void setChildren(List<TreeForm> children) {
		this.children = children;
	}
	
	public List<TreeForm> getChildren() {
		return children;
	}
	

}
