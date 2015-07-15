package tw.edu.chit.service;

public class InvalidAccountException extends Exception {
	
	public InvalidAccountException(String account) {
		super("Invalid account and/or password for account - " + account);
	}
}
