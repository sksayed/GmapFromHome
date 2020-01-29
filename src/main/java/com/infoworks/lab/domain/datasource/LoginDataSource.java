package com.infoworks.lab.domain.datasource;

import com.infoworks.lab.components.rest.source.RestDataSource;
import com.infoworks.lab.jsql.DataSourceKey;
import com.itsoul.lab.domain.models.auth.AuthResponse;
import com.itsoul.lab.domain.models.auth.Credential;
import com.itsoul.lab.interactor.exceptions.HttpInvocationException;
import com.itsoul.lab.interactor.exceptions.InvalideUserInfo;
import com.itsoul.lab.interactor.exceptions.UnauthorizedAccess;
import com.itsoul.lab.interactor.implementations.HttpAuthTemplate;
import com.itsoul.lab.interactor.interfaces.Interactor;
import com.vaadin.flow.component.UI;

public class LoginDataSource extends RestDataSource {

    public static final String TOKEN = "token_key";
    public static final String TENANT = "tenantID";
    public static final String AUTH_RESPONSE = "auth_response_key";

    public LoginDataSource() {}

    private static DataSourceKey keyContainer;

    public static DataSourceKey getKeys() {
        if (keyContainer == null) {
            DataSourceKey container = new DataSourceKey();
            container.set(DataSourceKey.Keys.SCHEMA, "http://");
            //
            String host = System.getenv("app.db.auth.host");
            container.set(DataSourceKey.Keys.HOST, host);
            //
            String port = System.getenv("app.db.auth.port");
            container.set(DataSourceKey.Keys.PORT, port);
            //
            String name = System.getenv("app.db.auth.name");
            container.set(DataSourceKey.Keys.NAME, name);
            //
            String tenantUserID = System.getenv("app.tenant.username");
            container.set(DataSourceKey.Keys.USERNAME, tenantUserID);
            //
            String password = System.getenv("app.tenant.password");
            container.set(DataSourceKey.Keys.PASSWORD, password);
            keyContainer = container;
        }
        return keyContainer;
    }

    public static boolean validate ( String username , String password) {

        try(HttpAuthTemplate login = Interactor.create(HttpAuthTemplate.class)){
            Credential credential = new Credential();
            credential.setUsername(username);
            credential.setPassword(password);
            credential.setDeviceUUID("MerchantHomeWebApp-"+username);
            credential.setTenantID(getKeys().get(DataSourceKey.Keys.USERNAME));

            AuthResponse authResponse = login.login(credential);//login.post(credential , "/login");
            if(authResponse != null && authResponse.getAccessToken() != null){
                UI.getCurrent().getSession().setAttribute(LoginDataSource.TOKEN , authResponse.getAccessToken());
                UI.getCurrent().getSession().setAttribute(LoginDataSource.TENANT , authResponse.getTenantID());
                UI.getCurrent().getSession().setAttribute(LoginDataSource.AUTH_RESPONSE , authResponse);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }

        if ( UI.getCurrent().getSession().getAttribute(LoginDataSource.TOKEN) != null) {
            return true ;
        }else {
            return false ;
        }

    }

    public static void logout() {
        Object token = UI.getCurrent().getSession().getAttribute(LoginDataSource.TOKEN);
        try (HttpAuthTemplate template = Interactor.create(HttpAuthTemplate.class)){
            Credential credential = new Credential();
            if(token != null) credential.setAccessToken(token.toString());
            credential.setTenantID(getKeys().get(DataSourceKey.Keys.USERNAME));
            AuthResponse response = template.logout(credential);
            if (response != null && response.isAuthorized()){
                System.out.println("Logout Successfull");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnauthorizedAccess unauthorizedAccess) {
            unauthorizedAccess.printStackTrace();
        } catch (InvalideUserInfo invalideUserInfo) {
            invalideUserInfo.printStackTrace();
        }
        UI.getCurrent().getSession().setAttribute(LoginDataSource.TOKEN , null);
        UI.getCurrent().getSession().setAttribute(LoginDataSource.TENANT , null);
        UI.getCurrent().getSession().setAttribute(LoginDataSource.AUTH_RESPONSE, null);
        UI.getCurrent().navigate("");
    }
}
