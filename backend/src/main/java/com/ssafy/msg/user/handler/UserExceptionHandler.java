package com.ssafy.msg.user.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ssafy.msg.user.exception.PasswordNotMatchException;
import com.ssafy.msg.user.exception.TokenExpiredException;
import com.ssafy.msg.user.exception.TokenInvalidException;
import com.ssafy.msg.user.exception.UserDuplicateException;
import com.ssafy.msg.user.exception.UserNotFoundException;
import com.ssafy.msg.user.exception.UserUnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class UserExceptionHandler {
	
	
	@ExceptionHandler({TokenInvalidException.class, UserUnauthorizedException.class, TokenExpiredException.class, UserNotFoundException.class, PasswordNotMatchException.class, MethodArgumentNotValidException.class, UserDuplicateException.class})
	public ResponseEntity<?> handleException(RuntimeException e) {
		
		HttpStatus httpStatus = null;
		
		if (e instanceof TokenInvalidException) {
			log.error("handleException() -> Exception : {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof UserUnauthorizedException) {
			log.error("handleException() -> Exception : {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof TokenExpiredException) {
			log.error("handleException() -> Exception : {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof UserNotFoundException) {
		    log.error("handleException() -> Exception : {}", e);
			httpStatus = HttpStatus.NOT_FOUND;
		} else if (e instanceof PasswordNotMatchException) {
			log.error("handleException() -> Exception : {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof UserDuplicateException) {
			log.error("handleException() -> Exception : {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			log.error("handleException() -> Exception : {}", e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
		
		return new ResponseEntity<>(httpStatus);
	}
}
