package com.ssafy.msg.user.exception;

public class UserDuplicateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDuplicateException() {
		super("UserDuplicateException");
	}
}
