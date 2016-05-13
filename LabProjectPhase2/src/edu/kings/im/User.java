package edu.kings.im;

/**
 * Class defines a user with a name, time they logged in, and time they were last sent a message.
 * 
 * @author Kathryn Lavelle
 * @author Dave Paupst
 *
 */
public class User {
	private String name;
	private int loggedInTime;
	private int lastSentTime;
	
	/**
	 * Constructor method for a new user.
	 * 
	 * @param name A String name.
	 * @param loggedInTime The time the user logged in.
	 */
	public User(String name, int loggedInTime) {
		this.name = name;
		this.loggedInTime = loggedInTime;
		lastSentTime = -1;
	}
	
	/**
	 * Accessor method for user's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Accessor method for user's logged in time.
	 */
	public int getLoggedInTime() {
		return loggedInTime;
	}
	
	/**
	 * Accessor method for user's last sent time.
	 */
	public int getLastSentTime() {
		return lastSentTime;
	}
	
	/**
	 * Sets this users logged in time.
	 * 
	 * @param loggedInTime The time the user logged in.
	 */
	public void setLoggedInTime(int loggedInTime) {
		this.loggedInTime = loggedInTime;
	}
	
	/**
	 * Sets the users last sent time.
	 * 
	 * @param lastSentTime The last time the user was sent a message.
	 */
	public void setLastSentTime(int lastSentTime) {
		this.lastSentTime = lastSentTime;
	}
	
	/**
	 * Returns the name of the user.
	 */
	@Override
	public String toString() {
		return name;
	}
	
	
}
