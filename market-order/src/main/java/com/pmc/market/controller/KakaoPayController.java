package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.service.KakaoPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "kakao Pay redirect Controller", tags = "카카오 페이 결제 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/kakaoPay")
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    @ApiOperation("카카오페이 리다이렉션 - 결제 요청 성공시")
    @GetMapping("/success")
    public ResponseEntity<ResponseMessage> kakaoPayCallbackSuccess(@RequestParam("pg_token") String pgToken) {
        return ResponseEntity.ok(ResponseMessage.success(kakaoPayService.approve(pgToken)));
    }

    @ApiOperation("카카오페이 리다이렉션 - 결제 요청 실패시")
    @GetMapping("/fail")
    public ResponseEntity<ResponseMessage> kakaoPayCallbackFail() {
        return ResponseEntity.ok(ResponseMessage.fail("KakaoPay 결제가 실패했습니다."));
    }

    @ApiOperation("카카오페이 리다이렉션 - 결제 요청 취소시")
    @GetMapping("/cancel")
    public ResponseEntity<ResponseMessage> kakaoPayCallbackCancel() {
        return ResponseEntity.ok(ResponseMessage.success("KakaoPay 결제를 취소했습니다."));
    }
}
