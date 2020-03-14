package com.infoworks.lab.app;


import com.infoworks.lab.AbstractDashBoard.DashBoardFinal;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "orderList" , layout = DashBoardFinal.class)
public class OrderList extends ExtendedDiv {
    public static final String ROUTE_NAME="orderList";
    Span span = new Span("this is OrderList page");

    OrderList(){
        add(span);
    }
}
