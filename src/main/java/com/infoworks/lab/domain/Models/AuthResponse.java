package com.infoworks.lab.domain.Models;

import com.infoworks.lab.rest.models.Response;

public class AuthResponse extends Response {

    private String accessToken;

    public AuthResponse() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
