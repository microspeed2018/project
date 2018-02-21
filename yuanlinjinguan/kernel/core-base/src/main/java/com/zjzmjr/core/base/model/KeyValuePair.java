package com.zjzmjr.core.base.model;

import java.io.Serializable;

/**
 * 键值对的简单模型类
 * 
 * @author oms
 * @version $Id: KeyValuePair.java, v 0.1 2016-9-30 上午10:07:44 oms Exp $
 */
public class KeyValuePair implements Serializable {

    /**  */
    private static final long serialVersionUID = -4248069407847941964L;

    private String key;

    private String value;

    private String memo;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "KeyValuePair [key=" + key + ", value=" + value + ", memo=" + memo + "]";
    }

}
