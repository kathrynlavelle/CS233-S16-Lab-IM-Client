package edu.kings.im.phase2;

import edu.kings.im.IMConnection;
import edu.kings.im.IllegalNameException;
import edu.kings.im.RealIMConnection;

/**
 * Control class for Kings Instant Messenger program.
 * 
 * @author Dave Paupst
 * @author Johnny Collado
 * @version 02/26/2016
 */
public class NullPointTexting {
	
	/**Represents the name for all users.*/
	private final static String BLAST;
	
	/** Keeps track of the current user. */
	private IMConnection user;
	
	static {
		 BLAST = "EVERYONE";
	}
	
	/** Constructor for NullPointTexting. */
	public NullPointTexting() {
		user = null;
	}
	
	/**
	 * Potential protected getter for User.
	 * 
	 * @return
	 */
	protected IMConnection getUser() {
		return user;
	}
	
	/**
	 * Potential protected setter for User.
	 * 
	 * @param daUser
	 */
	protected void setUser(IMConnection daUser) {
		user = daUser;
	}
	
	/**
	 * Sends a message to specified user.
	 * 
	 * @param message
	 * @param toWhom
	 */
	public void sendMessage(String message, String toWhom) {
		if(toWhom.equals(BLAST)) {
			user.spamEveryone(message);
		} else {
			user.sendMessage(message, toWhom);
		}
	}
	
	/**
	 * Receives messages...maybe??
	 * 
	 * @return
	 */
	public String receiveMessage() {
		//TODO: implement
		return "HELLLOOOOOO";
	}
	
	/**
	 *  Uses the userName to login to the server, prompts user as to whether or not
	 * 	the connection was successful with a message.
	 * 
	 * @param userName
	 * @return
	 */
	public boolean login(String userName) {
		IMConnection newConnection = RealIMConnection.getInstance(userName);
		return login(newConnection);
	}
	
	/** 
	 * Uses instance of a connection to login to the server,
	 * prompts whether or not there was success with a message
	 * 
	 * @param aConnection
	 * @return
	 */
	protected boolean login(IMConnection aConnection) {
		//TODO: talk to Dr. Jump, about statuslistener.
	
		boolean loginSuccessful = false;
		try {
			aConnection.connect();
		}
		catch(IllegalNameException ex) {
			//Print out invalid name message
			ex.printStackTrace();
		}
		
		return loginSuccessful;
	}
	 
	/**
	 * Logs the user out of the server.
	 */
	public void logout() {
		user.disconnect();
	}
	
}
