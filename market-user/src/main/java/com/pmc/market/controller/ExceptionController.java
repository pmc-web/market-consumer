package com.pmc.market.controller;

import com.pmc.market.error.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entryPoint")
    public ResponseEntity entryPointException() {
        throw new AuthorizationException("security error: 해당 리소스 접근 권한이 없습니다.");
    }
    @GetMapping(value = "/accessDenied")
    public ResponseEntity accessDeniedException() {
        throw new AccessDeniedException("보유한 권한으로 접근할 수 없는 리소스 입니다.");
    }
}