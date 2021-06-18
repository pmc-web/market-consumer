package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.service.KakaoPayService;
import com.pmc.market.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Order Controller", tags = "주문 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final KakaoPayService kakaoPayService;
    private final OrderService orderService;

    @ApiOperation("주문하기-카카오페이")
    @PostMapping("/kakaoPay")
    public void orderProductsByKakaoPay(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        kakaoPayService.requestOrder(orderRequestDto);
    }

    @ApiOperation("카카오페이 리다이렉션")
    @GetMapping("/kakaoPay/success")
    public ResponseEntity<?> kakaoPayCallback(@RequestParam("pg_token") String pgToken) {
        kakaoPayService.approve(pgToken);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("주문하기-무통장 입금")
    @PostMapping
    public ResponseEntity<?> orderProducts(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        orderService.makeOrder(orderRequestDto);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("주문내역")
    @GetMapping
    public ResponseEntity<?> getOrderList() {
        // user id 는 token 으로 조회
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("주문내역")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderInfo(@PathVariable("orderId") long orderId) {
        // user id 는 token 으로 조회
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("주문상태 변경")
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(ResponseMessage.success());
    }


}
