package com.zjzmjr.core.model.project;

import java.util.List;


public class ContractSubpackageInfo extends ContractSubpackage{

    /**  */
    private static final long serialVersionUID = 4526364542469987059L;
   
    private List<SubpackagePayment> subpackagePayment;

    
    public List<SubpackagePayment> getSubpackagePayment() {
        return subpackagePayment;
    }

    
    public void setSubpackagePayment(List<SubpackagePayment> subpackagePayment) {
        this.subpackagePayment = subpackagePayment;
    }


    @Override
    public String toString() {
        return "ContractSubpackageInfo [subpackagePayment=" + subpackagePayment + "]";
    }
    
}
