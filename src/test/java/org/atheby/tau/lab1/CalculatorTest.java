package org.atheby.tau.lab1;

import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CalculatorTest {
	
	private Calculator calc = new Calculator();
	
	@Rule public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void addTest() {
		
		assertEquals("5 + 5 = 10", 10, calc.add(5, 5));
		assertEquals("10 + 20 = 30", 30, calc.add(10, 20));
		assertEquals("2 + 5 = 7", 7, calc.add(2, 5));
	}
	
	@Test
	public void subTest() {
		
		assertEquals("5 - 2 = 3", 3, calc.sub(5, 2));
		assertEquals("17 - 4 = 13", 13, calc.sub(17, 4));
		assertEquals("12 - 3 = 9", 9, calc.sub(12, 3));
	}
	
	@Test
	public void multiTest() {
		assertEquals("3 * 4 = 12", 12, calc.multi(3, 4));
		assertEquals("23 * 3 = 69", 69, calc.multi(23, 3));
		assertEquals("6 * 10 = 60", 60, calc.multi(6, 10));
	}
	
	@Test
	public void divTest() {
		
		assertEquals("6 / 3 = 2", 2, calc.div(6, 3));
		assertEquals("15 / 3 = 5", 5, calc.div(15, 3));
		assertEquals("21 / 3 = 7", 7, calc.div(21, 3));
	}
	
	@Test
	public void greaterTest() {
		
		assertTrue("Should be true", calc.greater(10, 5));
		assertFalse("Should be false", calc.greater(5, 10));
	}
	
	@Test
	public void divArithExcTest() throws ArithmeticException {
		exception.expect(ArithmeticException.class);
		calc.div(6, 0);
	}
}
