package org.atheby.tau.lab2_3.messanger;

public interface MessageService {
		
	ConnectionStatus checkConnection(String server);
	
	SendingStatus send(String server, String message) throws MalformedRecipientException;

}
