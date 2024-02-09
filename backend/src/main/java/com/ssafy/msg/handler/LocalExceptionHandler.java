package com.ssafy.msg.handler;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ssafy.msg.user.exception.FollowException;
import com.ssafy.msg.user.exception.IdentifierException;
import com.ssafy.msg.user.exception.PasswordNotMatchException;
import com.ssafy.msg.user.exception.TokenExpiredException;
import com.ssafy.msg.user.exception.TokenInvalidException;
import com.ssafy.msg.user.exception.UserDuplicateException;
import com.ssafy.msg.user.exception.UserNotFoundException;
import com.ssafy.msg.user.exception.UserUnauthorizedException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@Profile("local")
public class LocalExceptionHandler {

	@ExceptionHandler({TokenInvalidException.class, UserUnauthorizedException.class, TokenExpiredException.class,
		UserNotFoundException.class, PasswordNotMatchException.class, MethodArgumentNotValidException.class,
		UserDuplicateException.class, IdentifierException.class, FollowException.class, Exception.class})
	public ResponseEntity<?> handleException(Exception e) {

		log.info("=======================< THROW >===============================");

		HttpStatus httpStatus = null;
		
		if (e instanceof TokenInvalidException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof UserUnauthorizedException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof TokenExpiredException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof UserNotFoundException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.NOT_FOUND;
		} else if (e instanceof PasswordNotMatchException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof UserDuplicateException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof IdentifierException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof MethodArgumentNotValidException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (e instanceof FollowException) {
			log.error("error = {}", e);
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			log.error("error = {}", e);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
		
		return new ResponseEntity<>(httpStatus);
	}
}