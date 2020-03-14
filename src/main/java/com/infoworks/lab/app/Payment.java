package com.infoworks.lab.app;

import com.infoworks.lab.AbstractDashBoard.DashBoardFinal;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "payment",layout = DashBoardFinal.class)
public class Payment extends ExtendedDiv {
    public static final String ROUTE_NAME="payment";
    Span span = new Span("This is payment Class");
    Payment(){
        add(span);
    }
}
