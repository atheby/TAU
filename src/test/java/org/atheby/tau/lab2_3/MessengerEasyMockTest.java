package org.atheby.tau.lab2_3;

import org.atheby.tau.lab2_3.app.Messenger;
import org.atheby.tau.lab2_3.messanger.*;
import org.easymock.*;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.*;

@RunWith(EasyMockRunner.class)
public class MessengerEasyMockTest {

    private final String VALID_SERVER = "inf.ug.edu.pl";
    private final String INVALID_SERVER = "inf.ug.edu.eu";

    private final String VALID_MESSAGE = "some message";
    private final String INVALID_MESSAGE = "ab";

    @Mock
    private MessageService msgSrvMock;

    @TestSubject
    private Messenger msn = new Messenger(msgSrvMock);

    @Test
    public void checkConnectionWithInvalidServerTest() {
        expect(msgSrvMock.checkConnection(INVALID_SERVER)).andReturn(ConnectionStatus.FAILURE);
        replay(msgSrvMock);
        assertThat(msn.testConnection(INVALID_SERVER), Matchers.is(1));
    }

    @Test
    public void checkConnectionWithValidServerTest() {
        expect(msgSrvMock.checkConnection(VALID_SERVER)).andReturn(ConnectionStatus.SUCCESS);
        replay(msgSrvMock);
        assertThat(msn.testConnection(VALID_SERVER), Matchers.is(0));
    }

    @Test
    public void sendWithInvalidMessageTest() throws MalformedRecipientException {
        expect(msgSrvMock.send(INVALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
        expect(msgSrvMock.send(VALID_SERVER, INVALID_MESSAGE)).andThrow(new MalformedRecipientException());
        replay(msgSrvMock);
        assertThat(msn.sendMessage(INVALID_SERVER, INVALID_MESSAGE), Matchers.is(2));
        assertThat(msn.sendMessage(VALID_SERVER, INVALID_MESSAGE), Matchers.is(2));
    }

    @Test
    public void sendWithConnectionProblemTest() throws MalformedRecipientException {
        expect(msgSrvMock.send(INVALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENDING_ERROR);
        replay(msgSrvMock);
        assertThat(msn.sendMessage(INVALID_SERVER, VALID_MESSAGE), Matchers.is(1));
    }

    @Test
    public void sendProperMessageTest() throws MalformedRecipientException {
        expect(msgSrvMock.send(VALID_SERVER, VALID_MESSAGE)).andReturn(SendingStatus.SENT);
        replay(msgSrvMock);
        assertThat(msn.sendMessage(VALID_SERVER, VALID_MESSAGE), Matchers.is(0));
    }
}
