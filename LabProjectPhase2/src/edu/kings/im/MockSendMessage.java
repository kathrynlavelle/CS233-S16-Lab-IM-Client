package edu.kings.im;

/**
 * This class simulates sending a message to a specified user.
 * 
 * @author guest-E4jE7j
 *
 */
public class MockSendMessage implements IMConnection {
	
	/**Keeps track of the message sent to Johnny.*/
	private String johnnyMessage;
	
	/**Keeps track of the message sent to Dave.*/
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
	 * 			The message being sent.
	 * @param arg1
	 * 			The receiver of the message.
	 */
	@Override
	public void sendMessage(String arg0, String arg1) {
		// TODO Auto-generated method stub
		if(arg1.equals("Johnny")) {
			johnnyMessage = arg0;
		}
		else if(arg1.equals("Dave")) {
			daveMessage = arg0;
		}
	}

	/**
	 * Getter for Johnny's message. Used for testing purposes ONLY.
	 * @return
	 * 		johnnyMessage, the message sent to johnny.
	 */
	protected String getJohnnyMessage() {
		return johnnyMessage;
	}
	
	/**
	 * Getter for Dave's message. Used for testing purposes ONLY.
	 * @return
	 * 		daveMessage, the message sent to dave.
	 */
	protected String getDaveMessage() {
		return daveMessage;
	}
	
	//Unused methods.
	@Override
	public void addConnectionListener(StatusListener arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void addIMEventListener(IMEventListener arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void connect() throws IllegalNameException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void spamEveryone(String arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
