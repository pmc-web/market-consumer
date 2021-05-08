package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.ShopInput;
import com.pmc.market.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Shop Controller", tags = "Shop 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    @ApiOperation("전체 마켓 리스트")
    @GetMapping("")
    public ResponseEntity<?> getAllShops() {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findAll()));
    }


    @ApiOperation("가게 등록하기")
    @PostMapping
    public ResponseEntity<?> makeShop(@ApiParam(value = "ShopInput 객체") @RequestBody @Valid ShopInput shopInput) {
        shopService.makeShop(shopInput);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("인기순 마켓 n개 조회")
    @GetMapping("/favorite")
    public ResponseEntity<?> getFavoriteShops(@RequestParam Integer count) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findFavorite(count)));
    }

}
