package main.java.org.atheby.tau.lab2.messanger;

public interface MessageService {
		
	ConnectionStatus checkConnection(String server);
	
	SendingStatus send(String server, String message) throws MalformedRecipientException;

}
