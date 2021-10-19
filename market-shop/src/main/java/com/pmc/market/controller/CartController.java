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
@RequestMapping("/carts")
@Slf4j
public class CartController {

    private final CartService cartService;

    @ApiOperation(value = "내 장바구니 ")
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseMessage> getUserCarts(@PathVariable(value = "userId") @ApiParam("유저 id") long userId) {
        return ResponseEntity.ok(ResponseMessage.success(cartService.getUserCarts(userId)));
    }

    @ApiOperation(value = "특정 마켓의 장바구니 정보")
    @GetMapping("/{userId}/shop")
    public ResponseEntity<ResponseMessage> getShopCart(@PathVariable(value = "userId") @ApiParam("유저 id") long userId, @RequestParam("shopId") long shopId) {
        return ResponseEntity.ok(ResponseMessage.success(cartService.getUserCartByShop(userId, shopId)));
    }

    @ApiOperation(value = "상품 장바구니 담기")
    @PostMapping("/{userId}")
    public ResponseEntity<ResponseMessage> addToCart(@PathVariable(value = "userId") @ApiParam("유저 id") long userId, @RequestBody CartProductRequestDto cartProductRequestDto) {
        cartService.addToCart(userId, cartProductRequestDto);
        return ResponseEntity.ok(ResponseMessage.success());
    }


    @ApiOperation(value = "상품 장바구니에서 삭제")
    @DeleteMapping("/{cartId}/product")
    public ResponseEntity<ResponseMessage> deleteToCart(@PathVariable(value = "cartId") @ApiParam("장바구니 id") long cartId, @RequestParam("cartProductId") long cartProductId) {
        cartService.deleteProductToCart(cartId, cartProductId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation(value = "장바구니 아이템 정보 수정")
    @PutMapping("/{cartId}/product")
    public ResponseEntity<ResponseMessage> updateCartProduct(@PathVariable(value = "cartId") @ApiParam("장바구니 id") long cartId, @RequestBody CartProductRequestDto cartProductRequestDto) {
        cartService.updateCartProduct(cartId, cartProductRequestDto);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation(value = "장바구니 전체 삭제")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ResponseMessage> deleteCart(@PathVariable(value = "cartId") @ApiParam("장바구니 id") long cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
