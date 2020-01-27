package com.infoworks.lab.domain.datasource;

import com.infoworks.lab.domain.Models.AuthResponse;
import com.infoworks.lab.domain.Models.Credential;
import com.infoworks.lab.domain.rest.templates.AuthTemplate;
import com.infoworks.lab.exceptions.HttpInvocationException;
import com.infoworks.lab.jsql.DataSourceKey;
import com.infoworks.lab.rest.template.Interactor;
import com.infoworks.lab.source.JsqlDataSource;
import com.infoworks.lab.source.RestDataSource;
import com.vaadin.flow.component.UI;

public class LoginDataSource extends RestDataSource {

    public static final String TOKEN = "token_key";
    public static final String AUTH_RESPONSE = "auth_response_key";

    public LoginDataSource() {
       // getjsqlDataSource();
    }

    private static DataSourceKey getDataSourceKey ()
    {
        DataSourceKey container = new DataSourceKey();
        container.set(DataSourceKey.Keys.SCHEMA, "http://");
        //
        String host = System.getenv("app.db.host");
        container.set(DataSourceKey.Keys.HOST, host);
        //
        String port = System.getenv("app.db.port");
        container.set(DataSourceKey.Keys.PORT, port);
        //
        String name = System.getenv("app.db.name");
        container.set(DataSourceKey.Keys.NAME, name);
        //
        String tenantUserID = System.getenv("app.db.username");
        container.set(DataSourceKey.Keys.USERNAME, tenantUserID);
        //
        String password = System.getenv("app.db.password");
        container.set(DataSourceKey.Keys.PASSWORD, password);
        return container;

    }
        //jsql data source has not been used
    private JsqlDataSource getjsqlDataSource (){
        JsqlDataSource jsqlDataSource = new RestDataSource();
        //Needs an executor
        LoginExecutor loginExecutor = new LoginExecutor(this.getDataSourceKey());
        //set the executor as the executor of the source
        jsqlDataSource.setExecutor(loginExecutor);
        return jsqlDataSource ;
    }

    public static boolean validate ( String username , String password) {

      try(AuthTemplate login = Interactor.create(AuthTemplate.class)){
          Credential credential = new Credential();
          credential.setUsername(username);
          credential.setPassword(password);
          credential.setDeviceUUID("MerchantHomeWebApp-"+username);
          credential.setTenantID(getDataSourceKey().get(DataSourceKey.Keys.USERNAME));

          AuthResponse authResponse = login.post(credential , "/login");
          if(authResponse != null && authResponse.getAccessToken() != null){
              UI.getCurrent().getSession().setAttribute(LoginDataSource.TOKEN , authResponse.getAccessToken());
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
}
