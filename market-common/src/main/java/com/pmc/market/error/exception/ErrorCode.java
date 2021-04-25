package com.pmc.market.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "C001", "올바르지 않는 입력 값입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "허용되지 않는 URL 입니다."),
    ENTITY_NOT_FOUND(400, "C003", "데이터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "서버에 요청 중 에러가 발생했습니다."),
    INVALID_TYPE_VALUE(400, "C005", "올바르지 않는 형식입니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "해당 요청은 금지되어있습니다."),
    DUPLICATE_ENTITY(409, "C007", "중복된 값이 있습니다."),

    // User
    EMAIL_SEND_ERROR(500, "U001", "이메일 전송에 실패했습니다.")
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

}
