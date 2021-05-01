package com.pmc.market.error.exception;

public class MarketUnivException extends BusinessException {
    public MarketUnivException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
