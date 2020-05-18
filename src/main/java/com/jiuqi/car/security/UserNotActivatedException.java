package com.jiuqi.car.security;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of TraversalTree not activated user trying to authenticate.
 */
public class UserNotActivatedException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
