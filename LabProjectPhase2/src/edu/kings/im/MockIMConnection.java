package edu.kings.im;

import edu.kings.cs.util.List;



/**
 * This class mocks a message listener for testing purposes.
 * 
 * @author Dave Paupst
 * @author Johnny Collado
 */
public class MockIMConnection implements IMConnection {

	/** Instance of the IMEventListener. */
	private IMEventListener messageListener;

	/**
	 * Accesses the userOnline method from messageListener.
	 * 
	 * @param userName
	 *            The user name.
	 */
	public void useUserOnline(String userName) {
		messageListener.userOnline(userName);
	}

	/**
	 * Accesses the userOffline method from messageListener.
	 * 
	 * @param userName
	 *            The user name.
	 */
	public void useUserOffline(String userName) {
		messageListener.userOffline(userName);
	}

	/**
	 * Accesses the messageReceived method from messageListener.
	 * 
	 * @param listOfMessages
	 *            A list of the messages used for testing.
	 */
	public void useMessageReceived(List<Message> listOfMessages) {
		messageListener.messagesReceived(listOfMessages);
	}

	@Override
	public void addIMEventListener(IMEventListener arg0) {
		messageListener = arg0;
	}

	@Override
	public void addConnectionListener(StatusListener arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void connect() throws IllegalNameException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void disconnect() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getUserName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendMessage(String arg0, String arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void spamEveryone(String arg0) {
		throw new UnsupportedOperationException();
	}

}
