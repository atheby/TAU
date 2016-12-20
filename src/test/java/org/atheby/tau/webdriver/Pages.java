package org.atheby.tau.webdriver;

import org.jbehave.web.selenium.*;
import org.atheby.tau.webdriver.pages.*;

public class Pages {

    private final WebDriverProvider driverProvider;
    private Home home;

    public Pages(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public Home home(){
        if ( home == null ){
            home = new Home(driverProvider);
        }
        return home;
    }
}
