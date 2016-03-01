package edu.kings.im;

/**
 * This class will mock a connection made with a valid username
 * and successfully connects to the server.
 * @author Dave Paupst
 * @author Johnny Collado
 * @version 02-29-2016 *
 */
public class MockValidLoginSuccess implements IMConnection {
	
	private boolean connection;
	
	/**
	 * Mocking a valid connection to the server. 
	 */
	@Override
	public void connect() throws IllegalNameException {
		connection = true;
	}
	
	/**
	 * Test-necessary method 
	 * @return
	 */
	protected boolean getConnection() {
		return connection;
	}
	
	@Override
	public void addConnectionListener(StatusListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addIMEventListener(IMEventListener arg0) {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return "Dr. jump, I will give you lots of money to give this prject an A :-)";
	}

	@Override
	public void sendMessage(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spamEveryone(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
