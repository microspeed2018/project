package com.zjzmjr.core.sms.util;


import java.util.Random;

/**
 * 字母数字的随机数获取
 * 
 * @author oms
 * @version $Id: RandomTools.java, v 0.1 2016-6-28 上午9:24:12 oms Exp $
 */
public class RandomTools {

    // 随机生成字符串(自定义长度)
    public static String getRandomString(int length) {
        String radStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer generateRandStr = new StringBuffer();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            int randNum = rand.nextInt(36);
            generateRandStr.append(radStr.substring(randNum, randNum + 1));
        }
        return generateRandStr + "";
    }

}
