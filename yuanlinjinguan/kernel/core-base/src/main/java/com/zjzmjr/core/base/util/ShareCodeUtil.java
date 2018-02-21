package com.zjzmjr.core.base.util;

import java.util.Random;

/**
 * 随机生成用户的邀请码
 * 
 * @author oms
 * @version $Id: ShareCodeUtil.java, v 0.1 2017-7-11 上午11:23:20 oms Exp $
 */
public class ShareCodeUtil {

    /** 自定义进制(0,1没有加入,容易与o,l混淆) */
    private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };

    /** (不能与自定义进制有重复) */
    private static final char b = 'o';

    /** 进制长度 */
    private static final int binLen = r.length;

    /** 序列最小长度 */
    private static final int s = 6;
    
    /**
     * 获取随机数
     * 
     * @param length
     * @return
     */
    public static String randomCode(int length){
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int cnt=0;cnt<length ;cnt++){
            builder.append(r[random.nextInt(33)]);
        }
        return builder.toString();
    }

    /**
     * 
     * 根据ID生成六位随机码
     * 
     * @param id
     *            ID
     * 
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        char[] buf = new char[32];
        int charPos = 32;
        while ((id / binLen) > 0) {
            int ind = (int) (id % binLen);
            // System.out.println(num + "-->" + ind);
            buf[--charPos] = r[ind];
            id /= binLen;
        }
        buf[--charPos] = r[(int) (id % binLen)];
        // System.out.println(num + "-->" + num % binLen);
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random rnd = new Random();
            for (int i = 1; i < s - str.length(); i++) {
                sb.append(r[rnd.nextInt(binLen)]);
            }
            str += sb.toString();
        }
        return str;
    }

    /**
     * 根据生成的邀请码获得用户的id
     * 
     * @param code
     * @return
     */
    public static long codeToId(String code) {
        char chs[] = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < binLen; j++) {
                if (chs[i] == r[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == b) {
                break;
            }
            if (i > 0) {
                res = res * binLen + ind;
            } else {
                res = ind;
            }
            // System.out.println(ind + "-->" + res);
        }
        return res;
    }

    public static void main(String[] args) {
        try {
            for (int cnt = 0; cnt < 100; cnt++) {
                System.out.println(toSerialCode(cnt+1));
                System.out.println(codeToId(toSerialCode(cnt+1)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
