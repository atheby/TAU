package org.atheby.tau.webdriver;

import org.jbehave.core.annotations.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class InitWebSteps {

    private final Pages pages;

    public InitWebSteps(Pages pages) {
        this.pages = pages;
    }

    @Given("user is on Home page")
    public void userIsOnHomePage(){
        pages.home().open();
    }

    @When("title is $title")
    public void checkIfOnPage(String title) {
        assertThat(pages.home().checkTitle(), is(title));
    }

    @Then("search for $search")
    public void searchFor(String search) {
        pages.home().search(search);
    }
}
