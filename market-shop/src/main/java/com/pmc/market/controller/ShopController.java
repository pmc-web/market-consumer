package com.pmc.market.controller;

import com.pmc.market.model.PageRequest;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "Shop Controller", tags = "마켓 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    @ApiOperation("전체 마켓 리스트")
    @GetMapping
    public ResponseEntity<ResponseMessage> getAllShops() {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findAll()));
    }

    @ApiOperation("가게 등록하기")
    @PostMapping
    public ResponseEntity<ResponseMessage> makeShop(@RequestBody @Valid ShopRequestDto shopRequestDto,
                                                    @RequestParam("files") MultipartFile[] files,
                                                    @AuthenticationPrincipal @ApiIgnore CustomUserDetails user) {
        shopService.makeShop(shopRequestDto, user.getUser(), files);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("인기순 마켓조회")
    @GetMapping("/popular")
    public ResponseEntity<ResponseMessage> getFavoriteShops(@PageableDefault @ApiIgnore PageRequest pageable) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findFavorite(pageable)));
    }

    @ApiOperation("새로 등록된 마켓조회")
    @GetMapping("/new")
    public ResponseEntity<ResponseMessage> getNewShops(@PageableDefault @ApiIgnore PageRequest pageable) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findNew(pageable)));
    }

    @ApiOperation("마켓 id 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getShopById(@ApiParam(value = "조회할 마켓 id") @PathVariable long id) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.getShopById(id)));
    }

    @ApiOperation("마켓 카테고리별 조회")
    @GetMapping("/category")
    public ResponseEntity<ResponseMessage> getShopsByCategory(@ApiParam(value = "카테고리 id") @RequestParam long id) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.getShopsByCategory(id)));
    }

    @ApiOperation("마켓 검색")
    @GetMapping("/search")
    public ResponseEntity<ResponseMessage> getShopsBySearch(@RequestParam String searchWord) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.getShopsBySearch(searchWord)));
    }

    @ApiOperation("마켓 정보 수정")
    @PostMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateShop(@RequestBody @Valid ShopRequestDto shopRequestDto,
                                                      @RequestParam("files") MultipartFile[] files,
                                                      @ApiParam("수정할 마켓 id") @PathVariable long id) {
        shopService.updateShop(shopRequestDto, id, files);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("마켓 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteShop(@ApiParam("삭제할 마켓 id") @PathVariable long id) {
        shopService.deleteShop(id);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("마켓 좋아요, 좋아요 해제")
    @PatchMapping("/{id}/like")
    public ResponseEntity<ResponseMessage> likeShop(@ApiParam("마켓 id") @PathVariable long id, @AuthenticationPrincipal @ApiIgnore CustomUserDetails user) {
        shopService.likeUpdateShop(id, user.getUser());
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
