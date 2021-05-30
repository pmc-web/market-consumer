package com.pmc.market.error.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String email) {
        super("해당 이메일(" + email + ")로 가입된 정보가 존재하지 않습니다. 이메일, 비밀번호를 확인하세요.");
    }
    public UserNotFoundException() {
        super("해당 유저가 존재하지 않습니다.");
    }
}
