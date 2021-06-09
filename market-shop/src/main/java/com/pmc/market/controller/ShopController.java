package com.pmc.market.controller;

import com.pmc.market.entity.User;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Shop Controller", tags = "마켓 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    @ApiOperation("전체 마켓 리스트")
    @GetMapping
    public ResponseEntity<?> getAllShops() {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findAll()));
    }

    @ApiOperation("가게 등록하기")
    @PostMapping
    public ResponseEntity<?> makeShop(@RequestBody @Valid ShopRequestDto shopRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        shopService.makeShop(shopRequestDto, user);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("인기순 마켓조회")
    @GetMapping("/popular")
    public ResponseEntity<?> getFavoriteShops(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "pageSize") int pageSize) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findFavorite(pageNumber, pageSize)));
    }

    @ApiOperation("새로 등록된 마켓조회")
    @GetMapping("/new")
    public ResponseEntity<?> getNewShops(@RequestParam(value = "pageNumber") int pageNumber, @RequestParam(value = "pageSize") int pageSize) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findNew(pageNumber, pageSize)));
    }

    @ApiOperation("마켓 id 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getShopById(@ApiParam(value = "조회할 마켓 id") @PathVariable long id) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.getShopById(id)));
    }

    @ApiOperation("마켓 카테고리별 조회")
    @GetMapping("/category")
    public ResponseEntity<?> getShopsByCategory(@ApiParam(value = "카테고리 id") @RequestParam long id) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.getShopsByCategory(id)));
    }

    @ApiOperation("마켓 검색")
    @GetMapping("/search")
    public ResponseEntity<?> getShopsBySearch(@RequestParam String searchWord) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.getShopsBySearch(searchWord)));
    }

    @ApiOperation("마켓 정보 수정")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateShop(@RequestBody @Valid ShopRequestDto shopRequestDto, @ApiParam("수정할 마켓 id") @PathVariable long id) {
        shopService.updateShop(shopRequestDto, id);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("마켓 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@ApiParam("삭제할 마켓 id") @PathVariable long id) {
        shopService.deleteShop(id);
        return ResponseEntity.ok(ResponseMessage.success());
    }

}
