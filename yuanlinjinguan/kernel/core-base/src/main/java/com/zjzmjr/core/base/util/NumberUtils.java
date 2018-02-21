package com.zjzmjr.core.base.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.zjzmjr.common.util.NumberUtil;

/**
 * 
 * 
 * @author oms
 * @version $Id: NumberUtils.java, v 0.1 2016-7-1 上午11:06:03 oms Exp $
 */
public class NumberUtils extends NumberUtil{

    /**
     * 判断是否是金额
     * 
     * @param money
     * @return
     */
    public static boolean isMoney(String money) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
        Matcher match = pattern.matcher(money);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * 字符串转换为BigDecimal
     * 
     * @param strVal
     * @return
     */
    public static BigDecimal string2BigDecimal(String strVal){
        if(StringUtils.isBlank(strVal)){
            return BigDecimal.ZERO;
        }
        return new BigDecimal(strVal);
    }
    
}
