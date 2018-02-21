package com.zjzmjr.core.model.company;

import java.util.List;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 基础数据表查询类
 * 
 * @author Administrator
 * @version $Id: BasicQuery.java, v 0.1 2016-10-26 上午10:20:46 Administrator Exp
 *          $
 */
public class BasicDataQuery extends BasePageQuery {

    private static final long serialVersionUID = -2026639247980367001L;

    /**
     * 主键编号
     */
    private Integer id;

    /**
     * 公司编号
     */
    private Integer companyId;

    /**
     * 类别编号
     */
    private Integer categoryId;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 区分编号
     */
    private Integer attributeId;

    /**
     * 区分名称
     */
    private String attributeName;

    /**
     * 类别id集合
     */
    private List<Integer> categoryIdLst;

    /**
     * 区分编号集合
     */
    private List<Integer> attributeIdLst;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<Integer> getCategoryIdLst() {
        return categoryIdLst;
    }

    public void setCategoryIdLst(List<Integer> categoryIdLst) {
        this.categoryIdLst = categoryIdLst;
    }

    public List<Integer> getAttributeIdLst() {
        return attributeIdLst;
    }

    public void setAttributeIdLst(List<Integer> attributeIdLst) {
        this.attributeIdLst = attributeIdLst;
    }

    @Override
    public String toString() {
        return "BasicDataQuery [id=" + id + ", companyId=" + companyId + ", categoryId=" + categoryId + ", categoryName=" + categoryName + ", attributeId=" + attributeId + ", attributeName=" + attributeName + ", categoryIdLst=" + categoryIdLst + ", attributeIdLst=" + attributeIdLst + "]";
    }

}
