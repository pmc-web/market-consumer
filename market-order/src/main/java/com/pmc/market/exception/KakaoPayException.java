package com.pmc.market.exception;

import com.pmc.market.error.exception.BusinessException;
import com.pmc.market.error.exception.ErrorCode;

public class KakaoPayException extends BusinessException {

    public KakaoPayException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public KakaoPayException(String message) {
        super(message, ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
