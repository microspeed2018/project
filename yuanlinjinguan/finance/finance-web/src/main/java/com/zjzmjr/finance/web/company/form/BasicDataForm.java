package com.zjzmjr.finance.web.company.form;

import java.util.List;

import com.zjzmjr.core.common.biz.BaseForm;


public class BasicDataForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = 6750305350193061128L;

    private Integer id;

    private Integer categoryId;

    private String categoryName;

    private Integer attributeId;

    private String attributeName;
    
    /**
     * 类别id集合
     */
    private List<Integer> categoryIdLst;
    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
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


    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
    public List<Integer> getCategoryIdLst() {
        return categoryIdLst;
    }


    
    public void setCategoryIdLst(List<Integer> categoryIdLst) {
        this.categoryIdLst = categoryIdLst;
    }


    @Override
    public String toString() {
        return "BasicForm [id=" + id + ", categoryId=" + categoryId + ", categoryName=" + categoryName + ", attributeId=" + attributeId + ", attributeName=" + attributeName + ", categoryIdLst=" + categoryIdLst + "]";
    }


}
