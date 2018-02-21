package com.zjzmjr.core.enums.admin;

/**
 * 事务类型, 用于事务类型表
 * 
 * @author oms
 * @version $Id: BusinessTypeEnum.java, v 0.1 2016-9-13 下午3:10:04 oms Exp $
 */
public enum BusinessTypeEnum {

    REGISTER(0,"注册"),
    RECHARGE(1,"充值"),
    WITHDRAWALS(2,"提现"),
    MODIFYLOGINPWD(3,"修改登录密码"),
    FINDLOGINPWD(4,"找回登录密码"),
    SETPAYPWD(5,"设定交易密码"),
    MODIFYPAYPWD(6,"修改交易密码"),
    FINDPAYPWD(7,"找回交易密码"),
    SETGESTUREPWD(8,"设置手势密码"),
    IDENTITYBINDING(9,"身份证绑定"),
    INSERTBANKCARD(10,"银行卡追加"),
    UPDATEBANKCARD(11,"银行卡变更"),
    INSERTCAR(12,"车辆追加"),
    UPDATECAR(13,"车辆变更"),
    CARUNBUNDLING(14,"车辆解绑"),
    AGENTAPPLYLOAN(15,"代理申请贷款"),
    APPLYINSURANCE(16,"申请保险"),
    REPAYLOAN(17,"还贷"),
    QUICKPAY(18, "快速还款"),
    MOBILEPAY(19, "手机充值"),
    SINOPECPAY(20, "加油卡充值"),
    APPLYLOAN(21,"申请贷款"),
    WITHHOLDING(22,"代扣"),
    QUICKPAY_SMS(23, "银行支付短信"),
    CHECKAGRICULTURE(24,"核实农险申请"),
    INTRESTVERIFY(25,"关注判定"),
    COMMUNICATION(26,"提交沟通单"),
    ACTIVEUSER(27,"激活用户"),
    CREDITREVERSEVERIFY(28,"车贷经理修改征信结果"),
    WITHHOLDSETTING(29, "设置代扣"),
    AGENTAPPLYINSURANCE(30,"代理保险申请"),
    APPLYCOSTCONFIRM(31,"保费确认"),
    INSURANCEFEEDBACK(32,"保险反馈"),
    REPLENISHDATA(33,"保险申请待补资料"),
    COMPLEMENTDATA(34,"保险资料补全"),
    REFUSEINSURANCE(35,"保险拒保"),
    ;

    private Integer value;

    private String message;

    private BusinessTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static BusinessTypeEnum getByValue(Integer value) {
        if (value != null) {
            for (BusinessTypeEnum enu : values()) {
                if (enu.value.equals(value)) {
                    return enu;
                }
            }
        }
        return null;
    }

    public String toString() {
        return value + "|" + message;
    }


}
