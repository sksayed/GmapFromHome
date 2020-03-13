package com.infoworks.lab.app;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.AppLayoutMenu;
import com.vaadin.flow.component.applayout.AppLayoutMenuItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;
import java.util.Map;


public class MainViews implements BeforeEnterObserver, RouterLayout {

    AppLayout appLayout = new AppLayout();
    private Tabs tabs = new Tabs();
    private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();
    private AppLayoutMenu appLayoutMenu;

    Span Header = new Span(" This is header ");

    public MainViews() {

        appLayoutMenu = appLayout.createMenu();
        appLayout.setBranding(Header);
        AppLayoutMenuItem appLayoutMenuItem = new AppLayoutMenuItem("Go");
        appLayoutMenu.addMenuItem(appLayoutMenuItem);

    }


    private void addMenuTab(String label, Class<? extends Component> target) {
        Tab tab = new Tab(new RouterLink(label, target));
        navigationTargetToTab.put(target, tab);
        tabs.add(tab);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {

    }

    @Override
    public Element getElement() {
        return null;
    }
}


/*@Route(value = "", layout = MainViews.class)
public class DefaultView extends Div {
    public DefaultView() {
        add(new Span("Default view content"));
    }
}

@Route(value = "admin", layout = MainView.class)
public class AdminView extends Div {
    public AdminView() {
        add(new Span("Admin view content"));
    }
}

@Route(value = "dashboard", layout = MainView.class)
public class DashboardView extends Div {
    public DashboardView() {
        add(new Span("Dashboard view content"));
    }
 }   */

