package com.zjzmjr.core.base.enums;

/**
 * 错误码枚举
 * 
 * @author zzhao E-mail:bovenjoy@163.com
 * @version createTime:2015-11-16 下午17:21:55 v.1
 */
public enum ErrorCodeEnum {

    // system
    SYSTEM_ERROR("system_error", "系统异常 请联系管理员"),

    PARAM_ERROR("param_error", "参数不正确"),
    RECORD_NOT_EXSIST("record_not_exsist", "暂无数据"),
    DB_OPR_ERROR("db_operator_err", "数据库操作失败"),
    RECORD_EXSIST("record_exsist", "数据库记录存在"),
    RECORD_CHANGE("record_change", "数据已被他人修改，请重新打开页面操作！"),
    UPCATE_LOANAPPLY_ERROR("upcate_loanApply_error", "更新贷款申请信息状态失败！"),
    NO_MECHANT("no_mechant", "该业务员没有设定经销商信息，无法录入资料，请联系车贷经理！"),
    NO_DEPART_PRO("no_depart_pro", "该部门尚未设定产品种类，无法录入资料，请联系车贷经理！"),
    NO_BASE_RATE("no_base_rate", "贷款银行尚未设定基准利率，无法录入资料，请联系车贷经理！"),
    
    // 交通接口
    TRAFFIC_THIRD_INVOKE_ERROR("traffic_third_invoke_error", "调用违章接口出错"),

    // 投资
    INVEST_SUCC("invest_succ", "购买成功"),

    USER_LOCKED("locked_user", "非正常用户"),

    PRODUCT_HAS_CLOSED("product_has_closed", "该产品已关闭购买"),

    BALANCE_NOT_ENOUGH("balance_not_enough", "余额不足"),

    HAS_INVESTED("has_invested", "该交易已完成"),

    NOT_INVEST_WITH_PER("not_invest_with_per", "请按产品的递增金额进行购买"),
    
    //系统管理
    ADMIN_NOT_EXIST("admin_not_exist","操作员不存在"),
    ADMIN_NOT_MATCH("admin_not_match","操作员不匹配"),
    RISKPARAM_NOT_EXIST("riskparam_not_exist","参数未维护"),

    // 用户
    USER_PHONE_IDENTITY_ERROR("user_phone_identity_error", "申请人手机已注册，但与姓名或身份证号不一致"), 
    USER_IDENTITY_ERROR("user_identity_error", "实名认证失败"), 
    USER_PHONE_ERROR("user_phone_error", "用户手机信息错误"), 
    USER_BANKCARD_STATE_ERROR("user_bankcard_state_error", "银行卡非正常状态"), 
    USER_BANKCARD_OWN_ERROR("user_bankcard_state_error", "请确认您是持卡人本人或所填信息无误"), 
    USER_ASSERT_ERROR("user_assert_error", "您的资产异常，请联系客服"),
    USER_IDENTITY_EXPIRED("identity_expired", "您的身份证有效期限已过!"),
    USER_IDENTITY_PHOTO_ERROR("user_identity_photo_error", "身份证校验失败"), 
    
    // 拉卡拉返回的错误消息
    USER_BANKCARD_NOT_EXIST("0005","银行卡信息输入有误，请确认后再提交"),
    USER_BANKCARD_NOT_MATCH("2001","注册用户的身份信息与持卡人的身份信息不一致，并请确保您的银行卡已开通网上支付功能"),
    
    // 银行卡
    USER_BANK_NOT_EXIST("1004", "银行卡信息输入有误，请重新绑定银行卡"),
    USER_BANK_DUPLICATE_EXIST("1005", "已经绑定了银行卡，如需更换，请直接修改银行卡"),
    
    DEFAULT("default","系统错误"),
    
    LOANINFO_SUBMIT("loanInfoSubmit","放款信息已经提交！！！"),
    VEHICLE_SUBMIT("vehicle_submit","上牌抵押信息已经提交！！！"),
    NEXT_STEP_SUBMIT("next_step_submit","下一步操作已经提交！！！");
    private String code;

    private String name;

    private ErrorCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ErrorCodeEnum getByValue(String code) {
        if (code != null) {
            for (ErrorCodeEnum enu : values()) {
                if (enu.code.equals(code)) {
                    return enu;
                }
            }
        }
        return null;
    }
}