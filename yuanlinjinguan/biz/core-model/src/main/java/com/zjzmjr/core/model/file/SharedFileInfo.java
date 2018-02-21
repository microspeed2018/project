package com.zjzmjr.core.model.file;

import com.zjzmjr.core.model.admin.Admin;


public class SharedFileInfo extends SharedFile{

    private static final long serialVersionUID = 2908500046082141533L;
    
    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }
    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "SharedFileInfo [admin=" + admin + "]";
    }
    
}
