package com.pmc.market.error.exception;

public class AuthorizationException extends BusinessException {

    public AuthorizationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
