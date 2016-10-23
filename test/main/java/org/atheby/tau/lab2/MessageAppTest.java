package main.java.org.atheby.tau.lab2;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import main.java.org.atheby.tau.lab2.app.*;
import main.java.org.atheby.tau.lab2.messanger.*;


public class MessageAppTest {

	Messanger messenger = new Messanger(new MessageServiceSimpleImpl());

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.eu";

	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "ab";

	@Test
	public void checkSendingMessage() {

		assertEquals(1, messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE));
		assertEquals(2, messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE));

		assertThat(messenger.sendMessage(VALID_SERVER, VALID_MESSAGE),
				either(equalTo(0)).or(equalTo(1)));
	}
}
