/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.base.page;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * @version $Id: PageBean.java, v 0.1 2015-10-28 上午11:01:47 Administrator Exp $
 */
public class PageBean implements Serializable {

    /**  */
    private static final long serialVersionUID = -7346195711721511360L;

    /**
     * 每页显示的记录数
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private int totalResults;

    /**
     * 当前页号
     */
    private int currentPage = 1;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 
     * @param totalResults
     *            总记录数
     * @param pageSize
     *            每页显示记录数
     */
    public PageBean(int totalResults, int pageSize, int currentPage) {
        this.currentPage = currentPage;
        this.totalResults = totalResults;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil(totalResults / (double) pageSize);
    }

    public PageBean(int pageSize, int currentPage) {
        this(0, pageSize, currentPage);
    }

    /**
     * 是否存在下一页
     * 
     * @return
     */
    public boolean hasNextPage() {
        if (this.currentPage >= this.totalPages)
            return false;
        else
            return true;
    }

    /**
     * 是否存在上一页
     * 
     * @return
     */
    public boolean hasPreviousPage() {
        if (this.currentPage <= 1)
            return false;
        else
            return true;
    }

    /**
     * 取得当前页号
     * 
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置当前页号
     * 
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 总页数
     * 
     * @return
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * 获得当前页的数据的第一行记录的行号
     * 
     * @return
     */
    public int getOffset() {
        return this.pageSize * (this.currentPage - 1);
    }

    /**
     * 获得当前页的数据的最后一行记录的行号
     * 
     * @return
     */
    public int getTop() {
        return this.pageSize + getOffset();
    }
    
    /**
     * 获取分页显示起始条数
     * 
     * @return
     */
    public int getStartRow(){
        return getOffset() + 1;
    }
    
    /**
     * 获取分页显示结束条数
     * 
     * @return
     */
    public int getEndRow(){
        if (totalResults < getTop()) {
            return totalResults;
        } else {
            return getTop();
        }
    }

    /**
     * 获取每页显示记录数
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页显示记录数
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 
     * @return 总记录数
     */
    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
