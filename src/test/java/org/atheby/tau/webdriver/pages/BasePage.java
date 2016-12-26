package org.atheby.tau.webdriver.pages;

import org.jbehave.web.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.util.concurrent.*;

public abstract class BasePage extends WebDriverPage {

    private WebDriverWait wait = new WebDriverWait(getDriverProvider().get(), 25);

    public BasePage(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open(String url) {
        get(url);
        manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    protected WebDriverWait getWebDriverWait() {
        return wait;
    }
}
