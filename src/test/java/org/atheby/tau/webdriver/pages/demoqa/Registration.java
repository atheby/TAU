package org.atheby.tau.webdriver.pages.demoqa;

import org.atheby.tau.webdriver.pages.*;
import org.jbehave.web.selenium.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.util.*;
import java.util.concurrent.*;

public class Registration extends BasePage {

    public Registration(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public String register(Map<String, String> data) {
        if(data.get("first").equals("random"))
            findElement(By.name("first_name")).sendKeys(getRandomString(10));
        else
            findElement(By.name("first_name")).sendKeys(data.get("first"));

        if(data.get("last").equals("random"))
            findElement(By.name("last_name")).sendKeys(getRandomString(10));
        else
            findElement(By.name("last_name")).sendKeys(data.get("last"));

        findElement(By.xpath("//input[@value=\'" + data.get("status") + "\']")).click();

        findElement(By.xpath("//input[@value=\'" + data.get("hobby") + "\']")).click();

        Select dropDown = new Select(findElement(By.id("dropdown_7")));
        if(data.get("country").equals("random")) {
            List<WebElement> countries = dropDown.getOptions();
            int country = getRandom(0, countries.size());
            dropDown.selectByIndex(country);
        }
        else
            dropDown.selectByValue(data.get("country"));

        dropDown = new Select(findElement(By.id("mm_date_8")));
        dropDown.selectByValue(String.valueOf(getRandom(1, 12)));
        dropDown = new Select(findElement(By.id("dd_date_8")));
        dropDown.selectByValue(String.valueOf(getRandom(1, 31)));
        dropDown = new Select(findElement(By.id("yy_date_8")));
        dropDown.selectByValue(String.valueOf(getRandom(1950, 2014)));

        if(data.get("phone").equals("random")) {
            findElement(By.id("phone_9")).sendKeys(String.valueOf(getRandom(10, 200)));
            findElement(By.id("phone_9")).sendKeys(String.valueOf(getRandom(100000000, 999999999)));
        }
        else
            findElement(By.id("phone_9")).sendKeys(data.get("phone"));

        if(data.get("username").equals("random"))
            findElement(By.name("username")).sendKeys(getRandomString(10));
        else
            findElement(By.id("username")).sendKeys(data.get("username"));

        if(data.get("email").equals("random"))
            findElement(By.id("email_1")).sendKeys(getRandomString(10) + "@" + getRandomString(5) + ".com");
        else
            findElement(By.id("email_1")).sendKeys(data.get("email"));

        if(data.get("password").equals("random")) {
            String pass = getRandomString(10);
            findElement(By.id("password_2")).sendKeys(pass);
            findElement(By.id("confirm_password_password_2")).sendKeys(pass);
        }
        else {
            findElement(By.id("password_2")).sendKeys(data.get("password"));
            findElement(By.id("confirm_password_password_2")).sendKeys(data.get("password"));
        }

        findElement(By.name("pie_submit")).click();

        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p[class^='piereg']")));

        return findElement(By.cssSelector("p[class^='piereg']")).getText();
    }

    private int getRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for(int x = 0; x < length; x++)
            sb.append((char) (getRandom(97, 122)));
        return sb.toString();
    }
}
