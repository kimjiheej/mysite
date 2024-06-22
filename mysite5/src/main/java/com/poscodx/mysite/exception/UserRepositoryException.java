package com.poscodx.mysite.exception;

public class UserRepositoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserRepositoryException() {
		super("UserRepositoryException Occuers");
	}
	
	public UserRepositoryException(String message) {
		super(message);
	}
}
