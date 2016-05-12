package edu.kings.im;

public class User {
	private String name;
	private int loggedInTime;
	private int lastSentTime;
	
	public User(String name, int loggedInTime) {
		this.name = name;
		this.loggedInTime = loggedInTime;
		lastSentTime = -1;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLoggedInTime() {
		return loggedInTime;
	}
	
	public int getLastSentTime() {
		return lastSentTime;
	}
	
	public void setLoggedInTime(int loggedInTime) {
		this.loggedInTime = loggedInTime;
	}
	
	public void setLastSentTime(int lastSentTime) {
		this.lastSentTime = lastSentTime;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
}
