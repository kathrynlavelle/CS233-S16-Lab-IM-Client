package edu.kings.im.phase2;

import edu.kings.im.IMConnection;

/**
 * Control class for Kings Instant Messenger program.
 * 
 * @author Dave Paupst
 * @author Johnny Collado
 * @version 02/26/2016
 */
public class NullPointTexting {
	
	/** Keeps track of the current user. */
	private IMConnection user;
	
	/** Constructor for NullPointTexting. */
	public NullPointTexting() {
		//TODO: implement.
	}
	
	/**
	 * Potential protected getter for User.
	 * 
	 * @return
	 */
	protected IMConnection getUser() {
		//TODO: implement.
		return null;
	}
	
	/**
	 * Potential protected setter for User.
	 * 
	 * @param daUser
	 */
	protected void setUser(IMConnection daUser) {
		//TODO: implement
	}
	
	/**
	 * Sends a message to specified user.
	 * 
	 * @param message
	 * @param toWhom
	 */
	public void sendMessage(String message, String toWhom) {
		//TODO: implement
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
	public String login(String userName) {
		//TODO: implement
		return "I'm logged in now!";
	}
	
	/** 
	 * Uses instance of a connection to login to the server,
	 * prompts whether or not there was success with a message
	 * 
	 * @param aConnection
	 * @return
	 */
	protected String login(IMConnection aConnection) {
		//TODO: implement
		return "Hey, what's up";
	}
	 
	/**
	 * Logs the user out of the server.
	 */
	public void logout() {
		//TODO: implement
	}
	
}
