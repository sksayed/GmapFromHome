package com.infoworks.lab.app;

import com.infoworks.lab.domain.datasource.LoginDataSource;
import com.itsoul.lab.client.GeoTracker;
import com.itsoul.lab.client.WebResource;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.Router;

import java.util.Map;

@Route("")
public class Login extends VerticalLayout {

    private LoginForm login ;
    public Login() {
        loginWindowInit();
        initListeners();
        add(login);
        setSizeFull();
        setHorizontalComponentAlignment(Alignment.CENTER ,login);
    }

    private void loginWindowInit () {
        this.login = new LoginForm();
        this.login.setForgotPasswordButtonVisible(false);
    }

    private void initListeners(){

        this.login.addLoginListener(new ComponentEventListener<AbstractLogin.LoginEvent>() {
            @Override
            public void onComponentEvent(AbstractLogin.LoginEvent loginEvent) {
                if(LoginDataSource.validate(loginEvent.getUsername() , loginEvent.getPassword())){

                    Map<String, String> env = System.getenv();
                    GeoTracker.shared().initialize(null, null);
                    Map target = GeoTracker.shared().updateServiceURLs(env.get(WebResource.API_PUBLIC_DNS.key()));
                    env.forEach((key, value) -> {
                        if (key.startsWith("com.itsoul.lab")){
                            target.put(key, value);
                        }
                    });
                    if (target.size() > 0) GeoTracker.shared().loadProperties(target);
                    System.out.println("API Gateway:" + WebResource.API_GATEWAY.value());

                    UI.getCurrent().navigate("dashboard");
                }else {
                    loginEvent.getSource().setError(true);
                }
            }
        });

    }


}
