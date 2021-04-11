package com.pmc.market.error.exception;

public class DuplicateEntityException extends BusinessException {
    /* https://deveric.tistory.com/62 참고 */
    public DuplicateEntityException(String message) {
        super(message, ErrorCode.DUPLICATE_ENTITY);
    }
}
