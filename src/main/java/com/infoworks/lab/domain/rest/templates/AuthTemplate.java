package com.infoworks.lab.domain.rest.templates;

import com.infoworks.lab.domain.Models.AuthResponse;
import com.infoworks.lab.client.jersey.HttpTemplate;
import com.it.soul.lab.sql.entity.Entity;

public class AuthTemplate extends HttpTemplate<AuthResponse, Entity> {

    @Override
    protected String host() {
        return System.getenv("app.db.auth.host");
    }

    @Override
    protected Integer port() {
        return Integer.valueOf(System.getenv("app.db.auth.port"));
    }

    public AuthTemplate() {
        super(AuthResponse.class, Entity.class);
    }

    @Override
    protected String api() {
        return System.getenv("app.db.auth.name");
    }

}
