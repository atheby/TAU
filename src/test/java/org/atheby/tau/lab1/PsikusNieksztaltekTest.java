package org.atheby.tau.lab1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.*;
import org.hamcrest.Matcher;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PsikusNieksztaltekTest {

	private Psikus psikus;
	private Integer input;
	private Matcher expected;
	
	public PsikusNieksztaltekTest(Psikus psikus, Integer input, Matcher expected) {
		this.psikus = psikus;
		this.input = input;
		this.expected = expected;
	}
	
	@Parameterized.Parameters
	public static Collection data() {
		return Arrays.asList(new Object[][] {
			{ new Gra(), 125, is(125) },
			{ new Gra(), -125, is(-125) },
			{ new Gra(), 334, anyOf(equalTo(834), equalTo(384)) },
			{ new Gra(), 376, anyOf(equalTo(876), equalTo(316), equalTo(379)) },
			{ new Gra(), 333, anyOf(equalTo(833), equalTo(383), equalTo(338)) }
		});
	}
	
	@Test
	public void nieksztaltekTest() {
		assertThat(expected.matches(psikus.nieksztaltek(input)), is(true));
	}
}
