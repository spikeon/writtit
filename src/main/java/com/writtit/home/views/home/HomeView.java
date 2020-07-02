package com.writtit.home.views.home;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.writtit.home.views.main.MainView;

@Route(value = "home", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Home")
@CssImport("styles/views/home/home-view.css")
public class HomeView extends Div {

    public HomeView() {
        setId("home-view");
        add(new Label("Future home of writtit.com"));
    }

}
