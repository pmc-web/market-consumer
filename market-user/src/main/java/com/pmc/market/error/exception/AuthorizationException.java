package com.pmc.market.error.exception;

public class AuthorizationException extends BusinessException {

    public AuthorizationException(String message) {
        super(message, ErrorCode.UNAUTHORIZED);
    }
}
