package com.study.springsecurity_basic.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidUserException extends AuthenticationException {
    public InvalidUserException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidUserException(String msg) {
        super(msg);
    }
}
