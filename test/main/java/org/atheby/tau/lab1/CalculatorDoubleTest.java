package main.java.org.atheby.tau.lab1;

import org.junit.*;
import static org.junit.Assert.*;

public class CalculatorDoubleTest {
	
	private CalculatorDouble calc = new CalculatorDouble();
	private static final double DELTA = 0.000000001;
	
	@Test
	public void addTest() {
		
		assertEquals("4.1 + 6.1 = 10.2", 10.2, calc.add(4.1, 6.1), DELTA);
		assertEquals("3.42142142414 + 4.24214214214 = 7.663563566", 7.663563566, calc.add(3.42142142414, 4.24214214214), DELTA);
	}
	
	@Test
	public void subTest() {
		
		assertEquals("3.42142142414 - 4.24214214214 = -0,820720718", -0.820720718, calc.sub(3.42142142414, 4.24214214214), DELTA);
		assertEquals("6.1 - 4.1 = 2.0", 2.0, calc.sub(6.1, 4.1), DELTA);
	}

	@Test
	public void multiTest() {
		
		assertEquals("6.55 * 6.554788 = 42.9338614", 42.9338614, calc.multi(6.55, 6.554788), DELTA);
		assertEquals("2.8512 * 5.141412 = 14.659193894", 14.659193894, calc.multi(2.8512, 5.141412), DELTA);
	}
	
	@Test
	public void divTest() {
		
		assertEquals("5.1425 / 2.12535 = 2.419601477", 2.419601477, calc.div(5.1425, 2.12535), DELTA);
		assertEquals("142.251122 / 2.1452 = 66.311356517", 66.311356517, calc.div(142.251122, 2.1452), DELTA);
	}
	
	@Test
	public void greaterTest() {
		
		assertFalse("Should be false", calc.greater(66.311356517, 66.43434333));
		assertTrue("Should be true", calc.greater(1.23213, 0.3213212));
	}
}
