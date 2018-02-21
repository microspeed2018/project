package com.zjzmjr.core.base.model;

import java.util.List;


public class AreaKeyValueInfo extends AreaKeyValue{

    /**  */
    private static final long serialVersionUID = -1754559415466032706L;
    
    List<KeyValuePairInfo> children;

    
    public List<KeyValuePairInfo> getChildren() {
        return children;
    }

    
    public void setChildren(List<KeyValuePairInfo> children) {
        this.children = children;
    }


    @Override
    public String toString() {
        return "AreaKeyValueInfo [children=" + children + "]";
    }
    
    
}
