package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.model.order.entity.OrderStatus;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@Api(value = "Order Controller", tags = "주문 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @ApiOperation("주문하기")
    @PostMapping
    public ResponseEntity<ResponseMessage> orderProducts(@RequestBody @Valid OrderRequestDto orderRequestDto, @AuthenticationPrincipal @ApiIgnore CustomUserDetails user) {
        orderService.makeOrder(orderRequestDto, user.getUser());
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("주문내역")
    @GetMapping
    public ResponseEntity<ResponseMessage> getOrderList(@AuthenticationPrincipal @ApiIgnore CustomUserDetails user) {
        return ResponseEntity.ok(ResponseMessage.success(orderService.getUserOrderList(user.getUser())));
    }

    @ApiOperation("주문내역 개별조회")
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseMessage> getOrderInfo(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(ResponseMessage.success(orderService.getOrder(orderId)));
    }

    @ApiOperation("주문상태 변경")
    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseMessage> updateOrderStatus(@PathVariable("orderId") long orderId, @ApiParam("변경할 OderStatus") @RequestBody OrderStatus status) {
        orderService.updateState(orderId, status);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("주문 취소")
    @PostMapping("/{orderId}")
    public ResponseEntity<ResponseMessage> cancelOrder(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(ResponseMessage.success(orderService.cancelOrder(orderId)));
    }
}
