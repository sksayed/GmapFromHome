package com.infoworks.lab.app;

import com.infoworks.lab.AbstractDashBoard.DashBoardFinal;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public abstract class ExtendedDiv extends Div {

   ExtendedDiv(){
       addClassName("main-content");
   }
}
