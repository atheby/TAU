package org.atheby.tau.lab4;

import org.atheby.tau.lab1.CalculatorDouble;
import org.jbehave.core.annotations.*;

import static org.junit.Assert.*;

public class CalculatorDoubleSteps {

    private CalculatorDouble calc;
    private double a, b;
    private static final double DELTA = 0.000000001;

    @Given("a calculator with real numbers")
    public void setup(){
        calc = new CalculatorDouble();
    }

    @When("numbers are $a and $b")
    public void setNumbers(double a, double b){
        setA(a);
        setB(b);
    }

    @Then("after add should return $result")
    public void afterAdd(double result) {
        assertEquals(result, calc.add(getA(), getB()), DELTA);
    }

    @Then("after subtract should return $result")
    public void afterSub(double result) {
        assertEquals(result, calc.sub(getA(), getB()), DELTA);
    }

    @Then("after multiply should return $result")
    public void afterMulti(double result) {
        assertEquals(result, calc.multi(getA(), getB()), DELTA);
    }

    @Then("after divide should return $result")
    public void afterDiv(double result) {
        assertEquals(result, calc.div(getA(), getB()), DELTA);
    }

    @Then("after compare should return that $a is greater than $b")
    public void compare(double a, double b) {
        assertTrue(calc.greater(a, b));
    }

    private double getA() {
        return a;
    }

    private void setA(double a) {
        this.a = a;
    }

    private double getB() {
        return b;
    }

    private void setB(double b) {
        this.b = b;
    }
}