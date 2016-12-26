package org.atheby.tau.webdriver;

import org.hamcrest.*;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.*;
import org.openqa.selenium.WebElement;

import java.util.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class PhpTravelsWebSteps {

    private final PhpTravelsWebPages phpTravelsWebPages;

    public PhpTravelsWebSteps(PhpTravelsWebPages phpTravelsWebPages) {
        this.phpTravelsWebPages = phpTravelsWebPages;
    }

    @Given("user opens $url")
    public void userIsOnHomePage(String url){
        phpTravelsWebPages.home().open(url);
    }

    @When("title is $a or $b or $c")
    public void checkTitle(String a, String b, String c) {
        assertThat(phpTravelsWebPages.home().getTitle(), Matchers.anyOf(Matchers.is(a), Matchers.is(b), Matchers.is(c)));
    }

    @Then("go to login page $url")
    public void goToLogin(String url) {
        phpTravelsWebPages.home().open(url);
    }

    @When("on \"Login\" page")
    public void checkIfLoginPage() {
        assertThat(phpTravelsWebPages.login().getTitle(), is("Login"));
    }

    @Then("enter email $email, password $password and submit")
    public void login(String email, String password) {
        phpTravelsWebPages.login().loginWithData(email, password);
    }

    @When("on \"My Account\" page")
    public void checkIfMyAccountPage() {
        assertThat(phpTravelsWebPages.login().getTitle(), is("My Account"));
    }

    @Then("open page $url")
    public void goToFlightsPage(String url) {
        phpTravelsWebPages.login().open(url);
    }

    @When("on \"Flights\" page")
    public void checkIfFlightsPage() {
        assertThat(phpTravelsWebPages.flights().getTitle(), is("Flights"));
    }

    @Then("search for a flight: $flights")
    public void searchFlight(ExamplesTable flights) {
        for (Map<String, String> row : flights.getRows()) {
            List<WebElement> results = phpTravelsWebPages.flights().searchFlight(row);
            assertThat(results.size(), greaterThan(0));
        }
    }
}
