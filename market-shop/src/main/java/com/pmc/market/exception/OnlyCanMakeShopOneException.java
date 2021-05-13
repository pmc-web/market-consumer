package com.pmc.market.exception;

import com.pmc.market.error.exception.DuplicateEntityException;

public class OnlyCanMakeShopOneException extends DuplicateEntityException {
    public OnlyCanMakeShopOneException(String message) {
        super(message);
    }
}
