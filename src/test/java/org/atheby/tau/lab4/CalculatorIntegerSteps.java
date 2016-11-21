package org.atheby.tau.lab4;

import org.atheby.tau.lab1.Calculator;
import org.jbehave.core.annotations.*;

import static org.junit.Assert.*;

public class CalculatorIntegerSteps {
	
	private Calculator calc;
	private int a, b;
	
	@Given("a calculator with decimal numbers")
	public void setup(){
		calc = new Calculator();
	}
	
	@When("numbers are $a and $b")
	public void setNumbers(int a, int b){
		setA(a);
		setB(b);
	}

	@Then("after add should return $result")
	public void afterAdd(int result) {
		assertEquals(result, calc.add(getA(), getB()));
	}

	@Then("after subtract should return $result")
	public void afterSub(int result) {
		assertEquals(result, calc.sub(getA(), getB()));
	}

	@Then("after multiply should return $result")
	public void afterMulti(int result) {
		assertEquals(result, calc.multi(getA(), getB()));
	}

	@Then("after divide should return $result")
	public void afterDiv(int result) {
		assertEquals(result, calc.div(getA(), getB()));
	}

	@Then("after compare should return that $a is greater than $b")
	public void compare(int a, int b) {
		assertTrue(calc.greater(a, b));
	}

	private int getA() {
		return a;
	}

	private void setA(int a) {
		this.a = a;
	}

	private int getB() {
		return b;
	}

	private void setB(int b) {
		this.b = b;
	}
}