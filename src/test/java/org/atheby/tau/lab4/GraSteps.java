package org.atheby.tau.lab4;

import org.atheby.tau.lab1.*;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.*;

import static org.hamcrest.MatcherAssert.assertThat;

public class GraSteps {

    private Gra gra;
    private int number;

    @Given("a gra")
    public void setup() {
        gra = new Gra();
    }

    @When("user enters $number")
    public void number(int number) {
        setNumber(number);
    }
    @Then("should remove random digit from it and output $a or $b or $c")
    public void removeDigit(int a, int b, int c) {
        assertThat(gra.cyfrokrad(getNumber()), Matchers.anyOf(Matchers.is(a), Matchers.is(b), Matchers.is(c)));
    }

    @When("user enters single digit like $single")
    public void single(int single) {
        setNumber(single);
    }

    @Then("nothing should happen")
    public void checkNull() {
        assertThat(gra.cyfrokrad(getNumber()), Matchers.is(Matchers.nullValue()));
    }

    @When("user enters number like $number")
    public void digitsToShift(int number) {
        setNumber(number);
    }

    @Then("after switch should return $a or $b or $c")
    public void switchNumber(int a, int b, int c) {
        try {
            assertThat(gra.hultajchochla(getNumber()), Matchers.anyOf(Matchers.is(a), Matchers.is(b), Matchers.is(c)));
        } catch(NieduanyPsikusException e) {}
    }

    @When("user enters number where one of the digit is present like $number")
    public void digitFromPattern(int number) {
        setNumber(number);
    }

    @When("user enters number without digit from pattern like $number")
    public void noPattern(int number) {
        setNumber(number);
    }

    @Then("the result should be $result")
    public void checkShift(int result) {
        assertThat(gra.nieksztaltek(getNumber()), Matchers.is(result));
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}