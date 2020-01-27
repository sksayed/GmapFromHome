package com.infoworks.lab.app;

import com.infoworks.lab.domain.datasource.LoginDataSource;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.Router;

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

                    UI.getCurrent().navigate("dashboard");
                }else {
                    loginEvent.getSource().setError(true);
                }
            }
        });

    }


}
