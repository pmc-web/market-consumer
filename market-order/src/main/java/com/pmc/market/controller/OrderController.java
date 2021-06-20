package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.OrderRequestDto;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> orderProducts(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        orderService.makeOrder(orderRequestDto);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("주문내역")
    @GetMapping
    public ResponseEntity<?> getOrderList() {
        // user id 는 token 으로 조회
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        return ResponseEntity.ok(ResponseMessage.success(orderService.getUserOrderList(user)));
    }

    @ApiOperation("주문내역 개별조회")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderInfo(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(ResponseMessage.success(orderService.getOrder(orderId)));
    }

    @ApiOperation("주문상태 변경")
    @PutMapping("/{orderId}") // TODO:
    public ResponseEntity<?> updateOrderStatus(@PathVariable("orderId") long orderId) {
        return ResponseEntity.ok(ResponseMessage.success());
    }

}
