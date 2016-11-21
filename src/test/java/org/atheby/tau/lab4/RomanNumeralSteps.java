package org.atheby.tau.lab4;

import org.atheby.tau.lab1.RomanNumeral;
import org.jbehave.core.annotations.*;

import static org.junit.Assert.assertEquals;

public class RomanNumeralSteps {

    private RomanNumeral rn;

    @Given("roman numeral")
    public void setup() {
    }

    @When("number is positive and equals $number")
    public void setNumber(int number) {
        rn = new RomanNumeral(number);
    }

    @Then("output should be $result")
    public void output(String $result) {
        assertEquals($result, rn.toString());
    }
}