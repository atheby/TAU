package org.atheby.tau.webdriver;

import org.atheby.tau.webdriver.pages.demoqa.*;
import org.jbehave.web.selenium.*;

public class DemoqaWebPages {

    private final WebDriverProvider driverProvider;
    private Home home;
    private Registration registration;
    private Droppable droppable;

    public DemoqaWebPages(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public Home home(){
        if ( home == null ){
            home = new Home(driverProvider);
        }
        return home;
    }

    public Registration registration(){
        if ( registration == null ){
            registration = new Registration(driverProvider);
        }
        return registration;
    }

    public Droppable droppable(){
        if ( droppable == null ){
            droppable = new Droppable(driverProvider);
        }
        return droppable;
    }
}
