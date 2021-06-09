package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.CartProductRequestDto;
import com.pmc.market.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "장바구니")
@RequestMapping("/users")
@Slf4j
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "내 장바구니 ")
    @GetMapping("/{userId}/cart")
    public ResponseEntity<?> getUserCart(@PathVariable(value = "userId") @ApiParam("유저 id") long userId) {
        cartService.getUserCart(userId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation(value = "상품 장바구니 담기")
    @PostMapping("/{userId}/cart/")
    public ResponseEntity<?> addToCart(@PathVariable(value = "userId") @ApiParam("유저 id") long userId, @RequestBody CartProductRequestDto cartProductRequestDto) {
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
