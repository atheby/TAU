package org.atheby.tau.webdriver.pages;

import org.jbehave.web.selenium.*;
import org.openqa.selenium.*;
import java.util.concurrent.*;

public class Home extends WebDriverPage {

    public Home(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void open(String url) {
        get(url);
        manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public String checkTitle() {
        return getTitle();
    }

    public void search(String s) {
        findElement(By.name("q")).sendKeys(s);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
