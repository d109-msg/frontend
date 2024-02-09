package com.ssafy.msg.handler;

import com.ssafy.msg.user.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@Profile({"blue", "green"})
public class BlueGreenExceptionHandler {


    @ExceptionHandler({TokenInvalidException.class, UserUnauthorizedException.class, TokenExpiredException.class,
            UserNotFoundException.class, PasswordNotMatchException.class, MethodArgumentNotValidException.class,
            UserDuplicateException.class, IdentifierException.class, FollowException.class, Exception.class})
    public ResponseEntity<?> handleException(Exception e) {

        log.info("=======================< THROW >===============================");

        HttpStatus httpStatus = null;

        if (e instanceof TokenInvalidException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof UserUnauthorizedException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof TokenExpiredException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof UserNotFoundException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.NOT_FOUND;
        } else if (e instanceof PasswordNotMatchException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof UserDuplicateException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof IdentifierException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof MethodArgumentNotValidException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if (e instanceof FollowException) {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            log.error("error = {}", e.toString());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(httpStatus);
    }
}
