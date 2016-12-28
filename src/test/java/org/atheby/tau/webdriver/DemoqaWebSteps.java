package org.atheby.tau.webdriver;

import org.jbehave.core.annotations.*;
import org.jbehave.core.model.*;
import java.util.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class DemoqaWebSteps {

    private final DemoqaWebPages demoqaWebPages;

    public DemoqaWebSteps(DemoqaWebPages demoqaWebPages) {
        this.demoqaWebPages = demoqaWebPages;
    }

    @Given("user opens $url")
    public void userIsOnHomePage(String url){
        demoqaWebPages.home().open(url);
    }

    @When("title is $title")
    public void checkTitle(String title) {
        assertThat(demoqaWebPages.home().getTitle(), is(title));
    }

    @Then("go to registration page $url and register: $data")
    public void createAccount(String url, ExamplesTable data) {
        for (Map<String, String> row : data.getRows()) {
            demoqaWebPages.home().open(url);
            String result = demoqaWebPages.registration().register(row);
            assertThat(result, anyOf(is("Thank you for your registration"), is("Error: Username already exists")));
        }
    }

    @When("on $url page")
    public void openPage(String url) {
        demoqaWebPages.droppable().open(url);
    }

    @Then("drag and drop an element")
    public void performDnD() {
        demoqaWebPages.droppable().dragAndDrop();
    }
}
