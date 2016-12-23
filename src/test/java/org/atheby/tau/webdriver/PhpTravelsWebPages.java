package org.atheby.tau.webdriver;

import org.atheby.tau.webdriver.pages.phptravels.*;
import org.jbehave.web.selenium.*;

public class PhpTravelsWebPages {

    private final WebDriverProvider driverProvider;
    private Home home;
    private Login login;

    public PhpTravelsWebPages(WebDriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public Home home(){
        if ( home == null ){
            home = new Home(driverProvider);
        }
        return home;
    }

    public Login login(){
        if ( login == null ){
            login = new Login(driverProvider);
        }
        return login;
    }
}
