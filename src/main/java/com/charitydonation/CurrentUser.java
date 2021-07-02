package com.charitydonation;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {
    private final com.charitydonation.entity.User user;

    public CurrentUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                       com.charitydonation.entity.User user){
        super(username, password, authorities);
        this.user = user;
    }

    public com.charitydonation.entity.User getUser(){
        return this.user;
    }

}
