/**
 * Yztz.cn Inc.
 * Copyright (c) 2010-2014 All Rights Reserved.
 */
package com.zjzmjr.core.base.page;

import java.io.Serializable;
import java.util.List;


/**
 * @author elliott
 * @version $Id: BaseQuery.java, v 0.1 2014-3-31 上午11:26:08 elliott Exp $
 */
public class BasePageQuery extends Base implements Serializable {

    private static final long serialVersionUID = -2352394640503653443L;

    private PageBean pageBean;

    private String orderBy;
    
    /**
     * 管辖区域
     */
    private List<Integer> multiList;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

    public List<Integer> getMultiList() {
        return multiList;
    }

    public void setMultiList(List<Integer> multiList) {
        this.multiList = multiList;
    }

}
