package com.zjzmjr.admin.web.holiday.form;

import com.zjzmjr.core.common.biz.BaseForm;
/**
 * 
 * 节假日表的form类
 * @author chenning
 * @version $Id: HolidayForm.java, v 0.1 2016-10-14 上午11:56:03 chenning Exp $
 */
public class HolidayForm extends BaseForm {

    private static final long serialVersionUID = -3838854487573315320L;
    
    //节假日表主键
    private Integer id;
    //节假日时间
    private String holidayTime;
    /**是否节假日*/
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayTime() {
        return holidayTime;
    }

    public void setHolidayTime(String holidayTime) {
        this.holidayTime = holidayTime;
    }
    
    public Integer getFlag() {
        return flag;
    }

    
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

   
    @Override
    public String toString() {
        return "HolidayForm [id=" + id + ", holidayTime=" + holidayTime + ", flag=" + flag + "]";
    }

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

}
