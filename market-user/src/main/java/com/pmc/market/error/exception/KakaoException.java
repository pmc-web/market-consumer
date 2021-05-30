package com.pmc.market.error.exception;

public class KakaoException extends BusinessException {
    public KakaoException(String message) {
        super(message, ErrorCode.OAUTH_ERROR);
    }
}
