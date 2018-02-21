package com.zjzmjr.core.model.weixin.wechat;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: Token.java, v 0.1 2016-6-27 上午10:11:34 Administrator Exp $
 */
public class Token {

    // 接口访问凭证
    private String accessToken;

    // 凭证有效期，单位：秒
    private int expiresIn;

    // 用户刷新access_token
    private String refreshToken;

    // 用户唯一标识
    private String openid;

    // 用户授权的作用域，使用逗号（,）分隔
    private String scope;

    private String unionid;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefresh_token() {
        return refreshToken;
    }

    public void setRefresh_token(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
