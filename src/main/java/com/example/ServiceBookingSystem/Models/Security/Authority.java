package com.example.ServiceBookingSystem.Models.Security;

import org.springframework.security.core.GrantedAuthority;
/**
 * Author : hassan shalash
 *
 * 4/5/2024  7:37
 *
 * */
public class Authority implements GrantedAuthority{    //TODO New Folder

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
