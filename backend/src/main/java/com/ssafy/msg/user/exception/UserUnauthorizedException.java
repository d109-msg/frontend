package com.ssafy.msg.user.exception;

public class UserUnauthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserUnauthorizedException() {
		super("UnauthorizedException");
	}
}
