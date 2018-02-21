package com.zjzmjr.finance.web.common.util;

import java.util.regex.Pattern;

public interface GeneralizeConstants {

    /**
     * 本站链接
     */
    String domainPattern = "^http?\\://\\w+\\.zjzmjr\\.com(/.*)?$";

    /**
     * 系统统计推广帐号
     */
    Integer systemGeneralizer = 666667;

    /**
     * 7天
     */
    int maxAge = 7 * 24 * 60 * 60;

    /**
     * cookie domain
     */
    String domain = ".cbylsj.com";

    /**
     * 推广的cookie名称
     */
    String generalizeCookieName = "X-GENERALIZE";

    /**
     * 推广来源Cookie名称
     */
    String generalizeRefferCookieName = "X-REFFER";

    /**
     * 推广cookie 格式
     */
    Pattern cookiePattern = Pattern.compile("^(\\d{1,})\\|(\\d{1,3}\\.\\d{1,3}.\\d{1,3}.\\d{1,3})$");

}
