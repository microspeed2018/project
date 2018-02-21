/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.enums.sms;


/**
 * 
 * @author Administrator
 * @version $Id: PointSymbolEnum.java, v 0.1 2015-11-3 下午6:51:28 Administrator Exp $
 */
public enum PointSymbolEnum {
    Postal("Postal","提现"),
    PayPassword("payPassword", "设置交易密码"),
    ModifyPayPassword("modifyPayPassword","修改交易密码"),
    Registered("registered","注册"),
    AgricultureApply("agricultureApply","农险申请"),
    Voice("voice", "语音验证"),
    ResetPassword("resetPassword","重置密码"),
    ModifyPhone("modifyPhone","修改绑定手机"),
    BuyFinancial("buyFinancial","购买理财"),
    PaySucess("paySucess","支付成功"),
    PayFail("payFail", "支付失败"),
    Auctions("auctions","流标"),
    CashWithdrawals("cashWithdrawals","提现到账"),
    Completed("completed", "到期"),
    BindEmail("bindEmail", "邮箱绑定"),
    BindMobile("bindMobile", "手机绑定"),
    ModifyEmail("modifyEmail", "修改绑定邮箱"),
    Interest("interest", "开始计息"),
    Repay("repay", "还款"),
    AppDownloadURL("appDownloadUrl", "App下载地址"),
    ActivateUser("activateUser","激活用户"),
    PayForCarLoan("payForLoan", "车贷还款"),
    SalesmanActivateUser("salesmanActivateUser","业务员激活用户"),
    RepayNotify("repayNotify","还款提醒"),
    FinalAuditLackData("finalAuditLackData","缺补资料提醒"),
    OVERDUECOLLECTION("overdueCollection","催收提醒");
        
    private String code;
    private String name;
    private PointSymbolEnum(String code,String name){
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }

    
    public String getName() {
        return name;
    }
    
    
    
}
