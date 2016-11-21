package org.atheby.tau.lab2_3;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.atheby.tau.lab2_3.app.Messenger;
import org.atheby.tau.lab2_3.messanger.MessageServiceSimpleImpl;
import org.junit.Test;

public class MessageAppTest {

	Messenger messenger = new Messenger(new MessageServiceSimpleImpl());

	private final String VALID_SERVER = "inf.ug.edu.pl";
	private final String INVALID_SERVER = "inf.ug.edu.eu";

	private final String VALID_MESSAGE = "some message";
	private final String INVALID_MESSAGE = "ab";

	@Test
	public void checkConnection() {
		assertEquals(1, messenger.testConnection(INVALID_SERVER));
		assertEquals(0, messenger.testConnection(VALID_SERVER));
	}

	@Test
	public void checkSendingMessage() {

		assertEquals(1, messenger.sendMessage(INVALID_SERVER, VALID_MESSAGE));
		assertEquals(2, messenger.sendMessage(VALID_SERVER, INVALID_MESSAGE));

		assertThat(messenger.sendMessage(VALID_SERVER, VALID_MESSAGE),
				either(equalTo(0)).or(equalTo(1)));
	}
}
