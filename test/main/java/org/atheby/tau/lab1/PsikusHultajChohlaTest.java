package main.java.org.atheby.tau.lab1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.*;
import org.hamcrest.Matcher;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PsikusHultajChohlaTest {

	@Rule public ExpectedException exception = ExpectedException.none();
	
	private Psikus psikus;
	private Integer input;
	private Matcher expected;
	
	public PsikusHultajChohlaTest(Psikus psikus, Integer input, Matcher expected) {
		this.psikus = psikus;
		this.input = input;
		this.expected = expected;
	}
	
	@Parameterized.Parameters
	public static Collection data() {
		return Arrays.asList(new Object[][] {
			{ new Gra(), 3, equalTo(NieduanyPsikusException.class) },
			{ new Gra(), -376, anyOf(equalTo(-736), equalTo(-673), equalTo(-367)) },
			{ new Gra(), 376, anyOf(equalTo(736), equalTo(673), equalTo(367)) }
		});
	}
	
	@Test
	public void hultajChohlaTest() throws NieduanyPsikusException{
		if(expected.matches(NieduanyPsikusException.class)) {
			exception.expect(NieduanyPsikusException.class);
			exception.expectMessage("Wystapil NieudanyPsikusException");
			psikus.hultajchochla(input);
		}
		else
			assertThat(expected.matches(psikus.hultajchochla(input)), is(true));
	}
}
