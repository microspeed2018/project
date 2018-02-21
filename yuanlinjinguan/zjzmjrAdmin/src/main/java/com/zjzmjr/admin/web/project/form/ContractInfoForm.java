package com.zjzmjr.admin.web.project.form;

import com.zjzmjr.web.mvc.form.AbstractForm;


public class ContractInfoForm extends AbstractForm{

    /**
     * 合同编号或者是合同编号的采番类型
     */
    private String contractNo;

    /**
     * 签约日期
     */
    private String signDate;
    
    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

}
