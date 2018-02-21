package com.zjzmjr.core.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对一些特定的接口进行访问次数的限制，
 * 例如一些第三方接口，由于每次调用都需要成本，为了成本控制，需要进行访问次数的限制
 * 
 * @author oms
 * @version $Id: AccessHeartBeat.java, v 0.1 2016-9-11 上午10:42:47 oms Exp $
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessHeartBeat {

    /**
     * 最大的访问次数的限制，默认最大是5次
     * 
     * @return
     */
    public int count() default 5;
    
    /**
     * 访问间隔，单位是秒。
     * 
     * @return
     */
    public int interval() default 1800;
    
    /**
     * 根据手机号码来限制访问次数
     * 
     * @return
     */
    public String mobile() default "";
    
    /**
     * 根据指定的值来进行访问限制
     * 
     * @return
     */
    public String value() default "";

    /**
     * 错误信息
     * 
     * @return
     */
    public String errorMsg() default "";
    
}
