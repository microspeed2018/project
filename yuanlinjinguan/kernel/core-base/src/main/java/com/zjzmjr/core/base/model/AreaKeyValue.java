package com.zjzmjr.core.base.model;

import java.io.Serializable;


public class AreaKeyValue implements Serializable{

    /**  */
    private static final long serialVersionUID = -6223287169028792376L;

    private Integer value;
    
    private String text;

    
    public Integer getValue() {
        return value;
    }

    
    public void setValue(Integer value) {
        this.value = value;
    }

    
    public String getText() {
        return text;
    }

    
    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "AreaKeyValue [value=" + value + ", text=" + text + "]";
    }
    
}
