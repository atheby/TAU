package org.atheby.tau.webdriver.pages.phptravels;

import org.atheby.tau.webdriver.pages.*;
import org.jbehave.web.selenium.*;
import org.openqa.selenium.*;

public class Login extends BasePage {

    public Login(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void loginWithData(String email, String password) {
        findElement(By.name("username")).sendKeys(email);
        findElement(By.name("password")).sendKeys(password);
        findElement(By.className("loginbtn")).click();
    }

    public String getUsername() {
        return findElement(By.xpath("//h3[@class='RTL']")).getText();
    }
}
