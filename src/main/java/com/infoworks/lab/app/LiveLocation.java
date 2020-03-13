package com.infoworks.lab.app;

import com.infoworks.lab.AbstractDashBoard.SujonDashBoard;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

import java.util.List;
@Route(value = "", layout = SujonDashBoard.class)
public class LiveLocation extends Div {
    Span span = new Span(" hello ");
    LiveLocation(){
       addClassName("main-content");
       add(span);
    }
}
