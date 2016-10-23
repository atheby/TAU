package main.java.org.atheby.tau.lab2.app;

import main.java.org.atheby.tau.lab2.messanger.*;

public class Messanger {

	private MessageService ms;

	public Messanger(MessageService ms) {
		this.ms = ms;
	}

	public int testConnection(String server) {
		switch (ms.checkConnection(server)) {
		case FAILURE:
			return 1;
		case SUCCESS:
			return 0;
		}
		return 1;
	}

	public int sendMessage(String server, String message) {

		int result = -1;

		try {
			SendingStatus sStatus = ms.send(server, message);
			switch (sStatus) {
			case SENT:
				result = 0;
				break;
			case SENDING_ERROR:
				result = 1;
				break;
			default:
				result = -1;
			}

		} catch (MalformedRecipientException e) {
			result = 2;
		}
		return result;
	}
}
