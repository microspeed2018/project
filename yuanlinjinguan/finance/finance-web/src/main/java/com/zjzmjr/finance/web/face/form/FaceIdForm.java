package com.zjzmjr.finance.web.face.form;

import com.zjzmjr.web.mvc.form.AbstractForm;


public class FaceIdForm extends AbstractForm{

    /**  */
    private static final long serialVersionUID = 6011934108870247849L;

    private String name;
    
    private String idCard;
    
    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getIdCard() {
        return idCard;
    }

    
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    @Override
    public String toString() {
        return "FaceIdForm [name=" + name + ", idCard=" + idCard + "]";
    }
    
}
