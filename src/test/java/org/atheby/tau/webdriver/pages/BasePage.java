package org.atheby.tau.webdriver.pages;

import org.jbehave.web.selenium.*;
import java.util.concurrent.*;

public abstract class BasePage extends WebDriverPage {

    public BasePage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open(String url) {
        get(url);
        manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
