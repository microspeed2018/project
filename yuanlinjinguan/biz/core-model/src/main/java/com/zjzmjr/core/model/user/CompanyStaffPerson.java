package com.zjzmjr.core.model.user;

import java.io.Serializable;
import java.util.List;

/**
 * 公司内部员工详细信息
 * 
 * @author oms
 * @version $Id: CompanyStaffPerson.java, v 0.1 2017-8-15 下午4:26:15 oms Exp $
 */
public class CompanyStaffPerson implements Serializable {

    /**  */
    private static final long serialVersionUID = 5789746787055810171L;

    /**
     * 员工基本信息
     */
    private List<StaffBasicInfo> staff;

    /**
     * 外部员工详细信息
     */
    private List<ExternalPerson> person;

    public List<StaffBasicInfo> getStaff() {
        return staff;
    }

    public void setStaff(List<StaffBasicInfo> staff) {
        this.staff = staff;
    }

    public List<ExternalPerson> getPerson() {
        return person;
    }

    public void setPerson(List<ExternalPerson> person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "CompanyStaffPerson [staff=" + staff + ", person=" + person + "]";
    }

}
