package com.zjzmjr.core.common.biz;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 基础form
 * 
 * @author oms
 * @version $Id: BaseForm.java, v 0.1 2016-5-26 下午2:42:47 oms Exp $
 */
public abstract class BaseForm extends AbstractForm {

    private static final long serialVersionUID = 6414463415020234963L;

    /**
     * 当前页数
     */
    private Integer page;

    /**
     * 每页件数
     */
    private Integer rows;

    public Integer getPage() {
        return page == null ? 1 : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows == null ? GenerateConstants.PAGE_SIZE : rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "BaseForm [page=" + page + ", rows=" + rows + "]";
    }

}
