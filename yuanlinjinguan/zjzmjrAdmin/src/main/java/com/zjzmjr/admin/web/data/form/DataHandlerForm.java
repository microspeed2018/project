package com.zjzmjr.admin.web.data.form;

import org.springframework.web.multipart.MultipartFile;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 外部数据处理form
 * 
 * @author oms
 * @version $Id: DataHandlerForm.java, v 0.1 2017-9-30 下午1:30:30 oms Exp $
 */
public class DataHandlerForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = 4799773235493646745L;

    /**
     * 导入文件地址
     */
    private MultipartFile excelAddress;

    /**
     * 导入excel的类型
     */
    private Integer handlerType;

    @Override
    public String resolveFiled(String field) {
        return null;
    }

    public MultipartFile getExcelAddress() {
        return excelAddress;
    }

    public void setExcelAddress(MultipartFile excelAddress) {
        this.excelAddress = excelAddress;
    }

    public Integer getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(Integer handlerType) {
        this.handlerType = handlerType;
    }

    @Override
    public String toString() {
        return "DataHandlerForm [excelAddress=" + excelAddress + ", handlerType=" + handlerType + "]";
    }

}
