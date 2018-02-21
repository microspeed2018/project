package com.zjzmjr.core.base.model;

import java.util.List;


public class KeyValuePairInfo extends AreaKeyValue{

    /**  */
    private static final long serialVersionUID = 4984129330180873601L;
    
    List<AreaKeyValue> children;

    
    public List<AreaKeyValue> getChildren() {
        return children;
    }

    
    public void setChildren(List<AreaKeyValue> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "KeyValuePairInfo [children=" + children + "]";
    }
    
    
}
