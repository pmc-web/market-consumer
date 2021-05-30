package com.pmc.market.error.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginFailException extends AuthenticationException {

    public LoginFailException(String status) {
        super("LoginFail : " + status + "상태이기 때문에 권한이 없습니다.");
    }

}
