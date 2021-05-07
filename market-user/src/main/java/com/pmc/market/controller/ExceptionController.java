package com.pmc.market.controller;

import com.pmc.market.error.exception.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entryPoint")
    public ResponseEntity entryPointException() {
        throw new AuthorizationException("security error: 해당 리소스 접근 한이 없습니다.");
    }
}