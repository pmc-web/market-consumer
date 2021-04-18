package com.pmc.market.controller;

import com.pmc.market.dto.ShopDto;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.ShopInput;
import com.pmc.market.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Shop Controller", tags = "Shop 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    @ApiOperation("전체 가게 리스트")
    @GetMapping("")
    public ResponseEntity<?> getAllShops() {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findAll()));
    }


    @ApiOperation("가게 등록하기")
    @PostMapping
    public ResponseEntity<?> makeShop(ShopInput shopInput) {
        shopService.makeShop(shopInput);
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
