package com.pmc.market.exception;

import com.pmc.market.error.exception.DuplicateEntityException;

public class OnlyCanMakeShopOne extends DuplicateEntityException {
    public OnlyCanMakeShopOne(String message) {
        super(message);
    }
}
