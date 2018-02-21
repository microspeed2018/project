package com.zjzmjr.core.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.zjzmjr.common.util.VerifyUtil;

/**
 * @author elliott
 * @version $Id: MaskUtil.java, v 0.1 2014-4-21 下午9:14:20 elliott Exp $
 */
public class MaskUtil {

    /**
     * 银行卡遮罩
     *
     * @param cardNo
     * @return
     */
    public static String maskBankCord(String cardNo) {
        if (StringUtils.isNotBlank(cardNo)) {
            String no = StringUtils.trimToEmpty(cardNo);
            if (no.length() == 16) {
                return no.replaceAll("^(\\d{4})\\d{8}(\\d{4})$", "$1 **** **** $2");
            } else if (no.length() == 19) {
                return no.replaceAll("^(\\d{4})\\d{11}(\\d{4})$", "$1** ***********$2");
            } else {
                String mask = "";
                for (int i = 0; i < no.length() - 8; i++) {
                    mask += "*";
                }
                return no.replaceAll("^(\\d{4})\\d{1,}(\\d{4})$", "$1" + mask + "$2");
            }
        }
        return cardNo;
    }

    /**
     * 从数据中查找银行卡,然后在遮罩
     *
     * @param msg
     * @return
     */
    public static String maskMsgBankCardMask(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            Pattern p = Pattern.compile("\\d{16,}");
            Matcher m = p.matcher(msg);
            if (m.find()) {
                return msg.replace(m.group(), maskBankCord(m.group()));
            }
        }
        return msg;
    }

    /**
     * 掩盖手机号码
     *
     * @param mobile
     * @return
     */
    public static String maskMobile(String mobile) {
        if (VerifyUtil.isMobile(mobile)) {
            return mobile.replaceAll("^(\\d{3})\\d{4}(\\d{4})$", "$1 **** $2");
        }
        return mobile;
    }

    /**
     * 邮件遮盖
     *
     * @param email
     * @return
     */
    public static String maskEmail(String email) {
        if (VerifyUtil.isEmail(email)) {
            Pattern p = Pattern.compile("^(\\w+)(@\\w+\\.\\w+(?:\\.\\w+)?)");
            Matcher m = p.matcher(email);
            if (m.find()) {
                String mname = m.group(1);
                int pre = Math.min(mname.length(), 3);
                String name = "";
                for (int i = 0; i < mname.length(); i++) {
                    if (i < pre) {
                        name += mname.charAt(i);
                    } else {
                        name += "*";
                    }
                }
                return name + m.group(2);
            }
        }
        return email;
    }

    /**
     * 遮盖身份证
     *
     * @return
     */
    public static String maskIdentity(String number) {
//        if (VerifyUtil.isIdentityCard(number)) {
//            return number.replaceAll("^(\\d{2})\\d{14}(\\w{2})$", "$1**** **** **** **$2");
//        }
        if (StringUtils.isNotBlank(number)) {
            return number.replaceAll("^(\\d{2})\\d{14}(\\w{2})$", "$1**** **** **** **$2");
        }
        return number;
    }

    /**
     * 遮盖用户名
     *
     * @param name
     * @return
     */
    public static String maskUsername(String name) {
        if (StringUtils.isNotBlank(name)) {
            if (name.length() > 2) {
                return name.replaceAll("^(.*).{2}$", "$1**");
            }
            return name.substring(0, 1) + "*";
        }
        return name;
    }

    /**
     * 交易账户遮罩
     *
     * @return
     */
    public static String maskInvestAccount(String account) {
        if (StringUtils.isNotBlank(account)) {
            return account.replaceAll("(.*交易密码：)\\w+(.*?)", "$1******$2");
        }
        return account;
    }

}
