package com.infoworks.lab.domain.Models;

import com.infoworks.lab.rest.models.Response;

public class AuthResponse extends Response {

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
