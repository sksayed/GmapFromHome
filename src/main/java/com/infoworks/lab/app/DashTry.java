package com.infoworks.lab.app;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.ClassList;
import com.vaadin.flow.router.Route;
@StyleSheet("styles/sujon_styles.css")
@Route("")
public class DashTry extends Div {

    Div header = new Div();
    HorizontalLayout headerLayout = new HorizontalLayout();
    Icon delimanIcon = new Icon(VaadinIcon.HOME);
    Button firstButton = new Button("First Button");
    Button secondButton = new Button("Second Button");
    Icon userIcon = new Icon(VaadinIcon.USER);
    Icon lastIcon = new Icon(VaadinIcon.QUESTION_CIRCLE);

    Div iconContainer = new Div();

    Div mainContent = new Div();
    Span demonSpan = new Span(" hello");

    //LEFT SIDE BAR
    Div leftSideBar = new Div();


    DashTry(){
        createHeader();
    }
    private void createHeader (){
        delimanIcon.setId("delimanIcon");
        headerLayout.setSpacing(true);
        headerLayout.setClassName("header_inner");
        firstButton.addClassName("button");
        firstButton.addClassName("button-rounded");

        iconContainer.add(delimanIcon);
        iconContainer.addClassName("logo_container");

        headerLayout.add( iconContainer, firstButton , secondButton , userIcon , lastIcon);
        header.add(headerLayout);
        header.addClassName("header_div");

        this.addClassName("wrapper");
        mainContent.setId("mainContent");
        mainContent.setClassName("mainContent");

        //left side bar
        leftSideBar.addClassName("Left_Side_Bar");
        mainContent.add(leftSideBar);
        mainContent.add(demonSpan);
        add(header, mainContent);

    }

}
