package org.atheby.tau.lab1;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.*;
import org.hamcrest.Matcher;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PsikusCyfrokradTest {

	private Psikus psikus;
	private Integer input;
	private Matcher expected;
	
	public PsikusCyfrokradTest(Psikus psikus, Integer input, Matcher expected) {
		this.psikus = psikus;
		this.input = input;
		this.expected = expected;
	}
	
	@Parameterized.Parameters
	public static Collection data() {
		return Arrays.asList(new Object[][] {
			{ new Gra(), 0, is(nullValue()) },
			{ new Gra(), 3, is(nullValue()) },
			{ new Gra(), -6, is(nullValue()) },
			{ new Gra(), 11, is(1) },
			{ new Gra(), -11, is(-1) },
			{ new Gra(), 354, anyOf(equalTo(54), equalTo(34), equalTo(35)) },
			{ new Gra(), -354, anyOf(equalTo(-54), equalTo(-34), equalTo(-35)) },
			{ new Gra(), 7897, anyOf(equalTo(897), equalTo(797), equalTo(787), equalTo(789)) }
		});
	}
	
	@Test
	public void cyfrokradTest() {
		assertThat(expected.matches(psikus.cyfrokrad(input)), is(true));
	}
}
