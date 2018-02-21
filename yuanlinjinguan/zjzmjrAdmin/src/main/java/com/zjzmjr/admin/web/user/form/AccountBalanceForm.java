package com.zjzmjr.admin.web.user.form;

import java.math.BigDecimal;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: AccountBalanceForm.java, v 0.1 2016-9-27 上午11:09:06 Administrator Exp $
 */
public class AccountBalanceForm extends BaseForm {
    
    private static final long serialVersionUID = -8208949435112297376L;
    
    private Integer id;

    /**
     *   userId 用户ID
     *   transactionType 交易类型
     *   recommendUserId 被推荐用户编号
     *   rewardSource 备注
     *   transactionAmount 交易金额
     *   remainingFunds 余额
     */
    private Integer userId;

    private Integer transactionType;

    private Integer recommendUserId;

    private String rewardSource;

    private BigDecimal transactionAmount;

    private BigDecimal remainingFunds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }
    
    public Integer getRecommendUserId() {
        return recommendUserId;
    }
    
    public void setRecommendUserId(Integer recommendUserId) {
        this.recommendUserId = recommendUserId;
    }
    
    public String getRewardSource() {
        return rewardSource;
    }
    
    public void setRewardSource(String rewardSource) {
        this.rewardSource = rewardSource;
    }
    
    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }
    
    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    
    public BigDecimal getRemainingFunds() {
        return remainingFunds;
    }
    
    public void setRemainingFunds(BigDecimal remainingFunds) {
        this.remainingFunds = remainingFunds;
    }

    @Override
    public String toString() {
        return "AccountBalanceQuery [id=" + id + ", userId=" + userId + ", transactionType=" + transactionType + ", recommendUserId=" + recommendUserId + ", rewardSource=" + rewardSource + ", transactionAmount=" + transactionAmount + ", remainingFunds=" + remainingFunds + "]";
    }

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

}
