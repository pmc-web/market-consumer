package com.pmc.market.error.exception;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String email) {
        super (email + "가 존재하지 않습니다.", ErrorCode.INVALID_INPUT_VALUE);
    }

    public UserNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
