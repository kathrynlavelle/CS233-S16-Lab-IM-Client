package edu.kings.im;

/**
 * This class simulates sending a message to a specified user.
 * 
 * @author Dave Paupst
 * @author Kate Lavelle
 *
 */
public class MockSendMessage implements IMConnection {

	/** Keeps track of the message sent to Johnny. */
	private String johnnyMessage;

	/** Keeps track of the message sent to Dave. */
	private String daveMessage;

	/**
	 * Constructor for the MockSendMessage.
	 */
	public MockSendMessage() {
		johnnyMessage = "";
		daveMessage = "";
	}

	/**
	 * This method will simulate a message being sent.
	 * 
	 * @param arg0
	 *            The message being sent.
	 * @param arg1
	 *            The receiver of the message.
	 */
	@Override
	public void sendMessage(String arg0, String arg1) {
		if (arg1.equals("Johnny")) {
			johnnyMessage = arg0;
		} else if (arg1.equals("Dave")) {
			daveMessage = arg0;
		}
	}

	/**
	 * Getter for Johnny's message. Used for testing purposes ONLY.
	 * 
	 * @return johnnyMessage, the message sent to johnny.
	 */
	public String getJohnnyMessage() {
		return johnnyMessage;
	}

	/**
	 * Getter for Dave's message. Used for testing purposes ONLY.
	 * 
	 * @return daveMessage, the message sent to dave.
	 */
	public String getDaveMessage() {
		return daveMessage;
	}

	// Unused methods.
	@Override
	public void addConnectionListener(StatusListener arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addIMEventListener(IMEventListener arg0) {
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
	public void spamEveryone(String arg0) {
		throw new UnsupportedOperationException();
	}
}
