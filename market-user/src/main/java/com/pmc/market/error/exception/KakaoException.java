package com.pmc.market.error.exception;

public class KakaoException extends BusinessException {
    public KakaoException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
