package org.atheby.tau.lab2_3;

import org.atheby.tau.lab2_3.app.Messenger;
import org.atheby.tau.lab2_3.messanger.*;
import org.hamcrest.Matchers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessengerMockitoTest {

    private final String VALID_SERVER = "inf.ug.edu.pl";
    private final String INVALID_SERVER = "inf.ug.edu.eu";

    private final String VALID_MESSAGE = "some message";
    private final String INVALID_MESSAGE = "ab";

    private Messenger msn;

    @Mock
    private MessageService msgSrvMock;

    @Before
    public void setup() {
        msn = new Messenger(msgSrvMock);
    }

    @Test
    public void checkConnectionWithInvalidServerTest() {
        when(msgSrvMock.checkConnection(INVALID_SERVER)).thenReturn(ConnectionStatus.FAILURE);
        assertThat(msn.testConnection(INVALID_SERVER), Matchers.is(1));
        verify(msgSrvMock, atLeastOnce()).checkConnection(INVALID_SERVER);
    }

    @Test
    public void checkConnectionWithValidServerTest() {
        when(msgSrvMock.checkConnection(VALID_SERVER)).thenReturn(ConnectionStatus.SUCCESS);
        assertThat(msn.testConnection(VALID_SERVER), Matchers.is(0));
        verify(msgSrvMock, atLeastOnce()).checkConnection(VALID_SERVER);
    }

    @Test
    public void sendWithInvalidMessageTest() throws MalformedRecipientException {
        when(msgSrvMock.send(INVALID_SERVER, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
        when(msgSrvMock.send(VALID_SERVER, INVALID_MESSAGE)).thenThrow(new MalformedRecipientException());
        assertThat(msn.sendMessage(INVALID_SERVER, INVALID_MESSAGE), Matchers.is(2));
        assertThat(msn.sendMessage(VALID_SERVER, INVALID_MESSAGE), Matchers.is(2));
        verify(msgSrvMock, atLeastOnce()).send(anyString(), anyString());
    }

    @Test
    public void sendWithConnectionProblemTest() throws MalformedRecipientException {
        when(msgSrvMock.send(INVALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENDING_ERROR);
        assertThat(msn.sendMessage(INVALID_SERVER, VALID_MESSAGE), Matchers.is(1));
        verify(msgSrvMock, atLeastOnce()).send(INVALID_SERVER, VALID_MESSAGE);
    }

    @Test
    public void sendProperMessageTest() throws MalformedRecipientException {
        when(msgSrvMock.send(VALID_SERVER, VALID_MESSAGE)).thenReturn(SendingStatus.SENT);
        assertThat(msn.sendMessage(VALID_SERVER, VALID_MESSAGE), Matchers.is(0));
        verify(msgSrvMock, atLeastOnce()).send(VALID_SERVER, VALID_MESSAGE);
    }
}
