package com.infoworks.lab.domain.Models;

import com.itsoul.lab.domain.base.Produce;

public class AuthResponse extends Produce {

    private String accessToken;
    private String tenantID;
    private String username;

    public AuthResponse() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
