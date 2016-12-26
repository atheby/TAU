package org.atheby.tau.webdriver.pages.phptravels;

import org.atheby.tau.webdriver.pages.*;
import org.jbehave.web.selenium.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.util.*;

public class Flights extends BasePage {

    private static final String FROM_XPATH = "//*[@id=\"flights\"]/ul[1]/li[1]";
    private static final String TO_XPATH = "//*[@id=\"flights\"]/ul[2]/li[1]";
    private static final String SRCHBTN_XPATH = "//*[@id=\"wg_flight_search\"]/fieldset/span[8]/button";

    public Flights(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public List<WebElement> searchFlight(Map<String, String> flight) {

        List<WebElement> results;

        switchTo().frame(0);

        findElement(By.id("flights_search_from_location_name")).clear();
        findElement(By.id("flights_search_from_location_name")).sendKeys(flight.get("from"));
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(FROM_XPATH)));
        findElement(By.xpath(FROM_XPATH)).click();

        findElement(By.id("flights_search_to_location_name")).clear();
        findElement(By.id("flights_search_to_location_name")).sendKeys(flight.get("to"));
        getWebDriverWait().until(ExpectedConditions.elementToBeClickable(By.xpath(TO_XPATH)));
        findElement(By.xpath(TO_XPATH)).click();

        findElement(By.id("flights_search_outbound_date")).clear();
        findElement(By.id("flights_search_outbound_date")).sendKeys(flight.get("outbound"));

        findElement(By.id("flights_search_inbound_date")).clear();
        findElement(By.id("flights_search_inbound_date")).sendKeys(flight.get("inbound"));

        Select cabinClass = new Select(findElement(By.id("flights_search_cabin_class")));
        cabinClass.selectByValue(flight.get("class").toLowerCase());

        findElement(By.xpath(SRCHBTN_XPATH)).click();

        getWebDriverWait().until(ExpectedConditions.textToBePresentInElement(By.className("results-header"), "Searching for Airlines"));
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.className("flight-count")));

        results = findElements(By.className("wan-flight"));

        switchTo().defaultContent();

        return results;
    }
}
