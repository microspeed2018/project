package com.zjzmjr.core.base.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zjzmjr.core.base.util.NumberUtils;

/**
 * 生成订单号的工具类
 * 
 * @author oms
 * @version $Id: OrderSerialNoUtil.java, v 0.1 2016-9-9 上午10:02:28 oms Exp $
 */
public class OrderSerialNoUtil {

    /**
     * 获取商户订单号 生成订单号 格式时间+8位用户id 不足前补0
     * 
     * @param userId
     * @return
     */
    public static String getMerOrderId(Integer userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + String.format("%08d", userId);
    }

    /**
     * 指定特定标识来获取商户订单号 生成订单号 格式时间+8位用户id 不足前补0 特别是当订单号会出现重复的情况下，利用这个标识可以避免重复
     * 
     * @param userId
     * @return
     */
    public static String getMerOrderId(String flg, Integer userId) {
        return getMerOrderId(userId).concat(flg);
    }

    /**
     * 拉卡拉的手续费。 
     * 计算用户提现的时候，手续费用，该手续是拉卡拉的模式
     * 
     * @param payMoney
     * @return
     */
    public static BigDecimal getChargeForFee(BigDecimal payMoney) {
        BigDecimal fee = payMoney.multiply(NumberUtils.string2BigDecimal("0.0018"));
        fee = fee.setScale(2, RoundingMode.CEILING);
        BigDecimal fixFee = NumberUtils.string2BigDecimal("2");
        if (fee.compareTo(fixFee) > 0) {
            return fee.add(fixFee);
        }
        return NumberUtils.string2BigDecimal("4");
    }

    /**
     * 连连的手续费
     * 当自动代扣的时候，需要收手续费的时候， 返回包含手续费在内的代扣金额
     * 
     * @param payMoney
     * @return
     */
    public static String getWithholdMoneyByFee(BigDecimal payMoney) {
        BigDecimal fee = payMoney.multiply(NumberUtils.string2BigDecimal("0.002"));
        fee = fee.setScale(2, RoundingMode.CEILING);
        BigDecimal fixFee = NumberUtils.string2BigDecimal("1");
        if (fee.compareTo(NumberUtils.string2BigDecimal("2")) > 0) {
            fixFee = fee.add(fixFee);
        } else {
            fixFee = NumberUtils.string2BigDecimal("3");
        }
        return String.valueOf(payMoney.add(fixFee));
    }

}
