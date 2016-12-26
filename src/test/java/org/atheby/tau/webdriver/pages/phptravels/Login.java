package org.atheby.tau.webdriver.pages.phptravels;

import org.atheby.tau.webdriver.pages.*;
import org.jbehave.web.selenium.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class Login extends BasePage {

    public Login(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void loginWithData(String email, String password) {
        findElement(By.name("username")).sendKeys(email);
        findElement(By.name("password")).sendKeys(password);
        findElement(By.className("loginbtn")).click();
        getWebDriverWait().until(ExpectedConditions.titleIs("My Account"));
    }
}
