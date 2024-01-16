package com.ssafy.msg.user.exception;

public class UserNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super("UserNotFoundException");
	}
}
