package com.pmc.market.controller;

import com.pmc.market.entity.User;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.NoticeRequestDto;
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

    @ApiOperation("인기순 마켓 최대 count 개 조회")
    @GetMapping("/favorite")
    public ResponseEntity<?> getFavoriteShops(@ApiParam(value = "조회할 갯수") @RequestParam int count) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findFavorite(count)));
    }

    @ApiOperation("새로 등록된 마켓 최대 count 개 조회")
    @GetMapping("/new")
    public ResponseEntity<?> getNewShops(@ApiParam(value = "조회할 갯수") @RequestParam int count) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.findNew(count)));
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

    // TODO : controller 분리 ? ex : ShopNoticeController
    @ApiOperation("마켓 공지사항 목록")
    @GetMapping("/{id}/notice")
    public ResponseEntity<?> getNoticeList(@ApiParam(value = "마켓 id") @PathVariable long id) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.getNoticeList(id)));
    }

    // TODO : url 에는 id 가 있어야 할 것 같은데 body에 id 를 같이 받는 방법이 더 좋을까?
    @ApiOperation("마켓 공지사항 작성")
    @PostMapping("/{id}/notice")
    public ResponseEntity<?> makeNotice(@ApiParam(value = "마켓 id") @PathVariable long id, @RequestBody @Valid NoticeRequestDto noticeRequestDto) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.insertNotice(id, noticeRequestDto)));
    }

    // TODO : ShopId 가 필요할까?
    @ApiOperation("마켓 공지사항 상세 조회")
    @GetMapping("/notice/{id}")
    public ResponseEntity<?> getNoticeDetail(@ApiParam(value = "공지사항 id") @PathVariable("id") long noticeId) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.getNotice(noticeId)));
    }

    @ApiOperation("마켓 공지사항 상세 수정")
    @PutMapping("/notice/{id}")
    public ResponseEntity<?> updateNotice(@ApiParam(value = "공지사항 id") @PathVariable("id") long noticeId, @RequestBody @Valid NoticeRequestDto noticeRequestDto) {
        return ResponseEntity.ok(ResponseMessage.success(shopService.updateNotice(noticeId, noticeRequestDto)));
    }

    @ApiOperation("마켓 공지사항 삭제")
    @DeleteMapping("/notice/{id}")
    public ResponseEntity<?> deleteNotice(@ApiParam(value = "공지사항 id") @PathVariable("id") long noticeId) {
        shopService.deleteNotice(noticeId);
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
