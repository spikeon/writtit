package com.writtit.home.views.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;import com.vaadin.flow.theme.lumo.Lumo;

import com.writtit.home.views.main.MainView;
import com.writtit.home.views.home.HomeView;

import org.vaadin.googleanalytics.tracking.EnableGoogleAnalytics;
import org.vaadin.googleanalytics.tracking.GoogleAnalyticsTracker;
import org.vaadin.googleanalytics.tracking.TrackerConfiguration;
import org.vaadin.googleanalytics.tracking.TrackerConfigurator;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "writtit", shortName = "writtit")
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@EnableGoogleAnalytics(value = "UA-171298506-1")
public class MainView extends AppLayout implements TrackerConfigurator, PageConfigurator {

    private final Tabs menu;

    public MainView() {
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, new DrawerToggle());
        menu = createMenuTabs();
        addToDrawer(menu);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Home", HomeView.class));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }

    @Override public void configureTracker(TrackerConfiguration trackerConfiguration) {
        trackerConfiguration.setCookieDomain("writtit.com");
    }

    @Override public void configurePage(InitialPageSettings settings) {
        settings.addInlineWithContents("<script data-ad-client=\"ca-pub-3654592274227692\" async src=\"https://pagead2" +
                ".googlesyndication.com/pagead/js/adsbygoogle.js\"></script>", InitialPageSettings.WrapMode.NONE);
    }
}
