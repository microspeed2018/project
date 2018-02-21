package com.zjzmjr.core.base.constants;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * 一些共同性的常量定义
 * 
 * @author oms
 * @version $Id: GenerateConstants.java, v 0.1 2016-8-17 上午8:37:52 oms Exp $
 */
public interface GenerateConstants {

    /**
     * 代理申请时的电话号码
     */
    String agent_mobile = "99999999999";
    
    /**
     * 数据迁移业务员默认手机号
     */
    String sale_mobile = "88888888888";

    /**
     * 代理申请时的默认密码
     */
    String agent_password = "azjzmjr*%";

    /** 事务主键 */
    String BUSINESS_KEY = "quick_pay_business_key";

    /**
     * 电审贷款额限制(一类)
     */
    BigDecimal DEBIT_AMOUNT = BigDecimal.valueOf(100000);

    BigDecimal DEBIT_AMOUNT_TWO = BigDecimal.valueOf(250000);

    // 首付比例变化幅度限制
    BigDecimal FIRST_SCALE_RANGE = BigDecimal.valueOf(2);

    // 最低首付比例限制
    BigDecimal FIRST_SCALE_LOWEST = BigDecimal.valueOf(30);

    String PAGE_BASH = "/WEB-INF/pages";

    /**
     * 成功
     */
    Integer SUCCESS = 1;

    /**
     * 失败
     */
    Integer FAILURE = 0;

    /**
     * 金额的上限值
     */
    String MONEY_MAX = "99999999";

    String token = "_zjzmjr";

    int PAGE_SIZE = 20;

    String CHECK_CODE = "check_code";

    String USER_INFO_SESSION = "zjzmjr_user_register_info_session_{mobile}";

    /**
     * 保存用户引导页面的实名信息
     */
    String IDENTITY_USER_INFO_SESSION = "identity_user_info_session_{userId}";

    /**
     * 保存地址
     */
    String SAVE_URL = "entrance";

    /**
     * 未认证
     */
    String unauthorized = "405";

    /**
     * 系统错误
     */
    String system = "900";

    /**
     * 数字0
     */
    Integer number_0 = 0;

    /**
     * 数字1
     */
    Integer number_1 = 1;

    /**
     * 数字2
     */
    Integer number_2 = 2;

    /**
     * 数字3
     */
    Integer number_3 = 3;

    /**
     * 数字4
     */
    Integer number_4 = 4;

    /**
     * 数字9
     */
    Integer number_9 = 9;

    /**
     * 数字22
     */
    Integer number_22 = 22;
    
    /**
     * 数字22
     */
    Integer number_275 = 275;

    /**
     * 逗号符号
     */
    String COMMA = ",";

    /**
     * 本站链接
     */
    String domainPattern = "^http?\\://\\w+\\.zjzmjr\\.com(/.*)?$";

    /**
     * 系统统计推广帐号
     */
    Integer systemGeneralizer = 999999;

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

    /**
     * 总还款额占比
     */
    Double totalPercent = 100.00;

    /**
     * 资料录入：提成单价(元)
     */
    Double custUnitPrice = 3.00;

    /**
     * 车辆补录：提成单价(元)
     */
    Double carUnitPrice = 2.00;
}
