package com.infoworks.lab.app;


import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "13131" )
public class OrderList extends ExtendedDiv {
    Span span = new Span("this is OrderList page");

    OrderList(){
        addToMain(span);
    }
}
