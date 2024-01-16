package com.ssafy.msg.user.exception;

public class PasswordNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PasswordNotMatchException() {
		super("PasswordNotMatchException");
	}
}
