package org.atheby.tau.webdriver;

import org.hamcrest.*;
import org.jbehave.core.annotations.*;
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

    @When("title is $a or $b")
    public void checkTitle(String a, String b) {
        assertThat(phpTravelsWebPages.home().getTitle(), Matchers.anyOf(Matchers.is(a), Matchers.is(b)));
    }

    @Then("go to login page $url")
    public void goToLogin(String url) {
        phpTravelsWebPages.home().open(url);
    }

    @When("on Login page")
    public void checkIfLoginPage() {
        assertThat(phpTravelsWebPages.login().getTitle(), is("Login"));
    }

    @Then("enter email $email, password $password and submit")
    public void login(String email, String password) {
        phpTravelsWebPages.login().loginWithData(email, password);
    }

    @AfterStories
    public void afterLoginAttempt() {
        assertThat(phpTravelsWebPages.login().getUsername(), is("Hi, John Smith"));
    }
}
