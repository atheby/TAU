package org.atheby.tau.lab1;

import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class RomanNumeralTest {
	
	@Rule public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void constructorWithPositiveArgumentTest() {
		assertEquals(23, new RomanNumeral(23).getNumber());
		assertEquals("III", new RomanNumeral(3).toString());
		assertEquals("XXIII", new RomanNumeral(23).toString());
		assertEquals("MMXLVIII", new RomanNumeral(2048).toString());
	}
	
	@Test
	public void constructorWithLessOrEvenZeroArgumentTest() throws IllegalArgumentException {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Number must be greater than 0");
		new RomanNumeral(-5);
		new RomanNumeral(0);
	}
}
