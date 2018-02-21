package com.zjzmjr.core.model.menu;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 
 * @author liwen
 * @version $Id: MenuNodeDTO.java, v 0.1 2015-10-25 上午9:51:00 liwen Exp $
 */
public class MenuNode implements Serializable {

    private static final long serialVersionUID = 2303651455442933019L;

    private Integer id;

    private String text;

    private String url;

    private boolean checked;

    private Integer order;

    private Integer project;

    private List<MenuNode> children = new LinkedList<MenuNode>();

    public MenuNode() {
    }

    public MenuNode(Integer id, String text, String url, Integer order, boolean checked) {
        this.id = id;
        this.text = text;
        this.url = url;
        this.order = order;
        this.checked = checked;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }

    public void addChild(MenuNode child) {
        this.children.add(child);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MenuNode other = (MenuNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MenuNode [id=" + id + ", text=" + text + ", url=" + url + ", checked=" + checked + ", order=" + order + ", project=" + project + ", children=" + children + "]";
    }

}
