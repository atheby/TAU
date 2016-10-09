import org.atheby.tau.lab1.Calculator;
import static org.junit.Assert.assertEquals;
import org.junit.*;

public class CalculatorTest {
	
	public Calculator calc = new Calculator();
	
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
		
		assertEquals("5 > 3 = true", true, calc.greater(5, 3));
		assertEquals("11 > 20 = false", false, calc.greater(11, 20));
		assertEquals("21 > 17 = true", true, calc.greater(21, 17));
	}
	
	@Test(expected=ArithmeticException.class)
	public void divArthExcTest() {
		assertEquals("3 / 0", 0, calc.div(3, 0));
	}
}
