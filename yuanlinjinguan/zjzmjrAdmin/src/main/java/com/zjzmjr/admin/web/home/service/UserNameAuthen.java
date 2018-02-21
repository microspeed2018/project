package com.zjzmjr.admin.web.home.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zjzmjr.core.model.admin.Admin;

/**
 * 
 * 
 * @author liwen
 * @version $Id: UserNameAuthen.java, v 0.1 2015-11-1 上午10:34:25 liwen Exp $
 */
public class UserNameAuthen extends Admin implements UserDetails {

    /**  */
    private static final long serialVersionUID = 4614239572759513760L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
