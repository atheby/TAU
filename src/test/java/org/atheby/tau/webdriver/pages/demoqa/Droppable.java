package org.atheby.tau.webdriver.pages.demoqa;

import org.atheby.tau.webdriver.pages.*;
import org.jbehave.web.selenium.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;

public class Droppable extends BasePage {

    public Droppable(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    public void dragAndDrop() {
        Actions builder = new Actions(getDriverProvider().get());
        WebElement draggable = findElement(By.id("draggableview"));
        WebElement droppable = findElement(By.id("droppableview"));

        builder.clickAndHold(draggable)
                .moveToElement(droppable)
                .release()
                .build()
                .perform();
    }
}
