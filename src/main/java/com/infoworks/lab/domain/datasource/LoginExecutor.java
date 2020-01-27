package com.infoworks.lab.domain.datasource;

import com.infoworks.lab.components.rest.RestExecutor;
import com.infoworks.lab.jsql.DataSourceKey;

public class LoginExecutor extends RestExecutor {
    public LoginExecutor(DataSourceKey sourceKey) {
        super(sourceKey);
    }
}
