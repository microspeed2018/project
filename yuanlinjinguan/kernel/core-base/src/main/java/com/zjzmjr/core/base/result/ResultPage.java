/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.base.result;

import java.util.List;

import com.zjzmjr.core.base.page.PageBean;

/**
 * 
 * @author Administrator
 * @version $Id: ResultPage.java, v 0.1 2015-10-28 上午11:13:49 Administrator Exp
 *          $
 */
public class ResultPage<T> extends Result {

    /**  */
    private static final long serialVersionUID = 6654580029089922640L;

    private PageBean page;

    private List<T> list;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
