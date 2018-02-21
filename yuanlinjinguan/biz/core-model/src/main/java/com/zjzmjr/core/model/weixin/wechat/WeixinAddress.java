package com.zjzmjr.core.model.weixin.wechat;

import java.io.Serializable;

public class WeixinAddress implements Serializable {

    private static final long serialVersionUID = 7545836721583661471L;

    private String address;

    private String business;

    private String sematicDescription;

    private String latitude;

    private String longitude;

    public String getSematicDescription() {
        return sematicDescription;
    }

    public void setSematicDescription(String sematicDescription) {
        this.sematicDescription = sematicDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "WeixinAddress [address=" + address + ", business=" + business + ", sematicDescription=" + sematicDescription + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }

}
