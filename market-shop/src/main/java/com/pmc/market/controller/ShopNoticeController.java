package com.pmc.market.controller;

import com.pmc.market.domain.shop.dto.NoticeRequestDto;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.service.ShopNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Shop Notice Controller", tags = "마켓 공지사항 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopNoticeController {

    private final ShopNoticeService shopNoticeService;

    @ApiOperation("마켓 공지사항 목록")
    @GetMapping("/{id}/notice")
    public ResponseEntity<ResponseMessage> getNoticeList(@ApiParam(value = "마켓 id") @PathVariable long id) {
        return ResponseEntity.ok(ResponseMessage.success(shopNoticeService.getNoticeList(id)));
    }

    @ApiOperation("마켓 공지사항 작성")
    @PostMapping("/{shopId}/notice")
    public ResponseEntity<ResponseMessage> makeNotice(@ApiParam(value = "마켓 id") @PathVariable("shopId") long id, @RequestBody @Valid NoticeRequestDto noticeRequestDto) {
        return ResponseEntity.ok(ResponseMessage.success(shopNoticeService.insertNotice(id, noticeRequestDto)));
    }

    @ApiOperation("마켓 공지사항 상세 조회")
    @GetMapping("/{shopId}/notice/{noticeId}")
    public ResponseEntity<ResponseMessage> getNoticeDetail(@ApiParam(value = "마켓 id") @PathVariable("shopId") long shopId, @ApiParam(value = "공지사항 id") @PathVariable("noticeId") long noticeId) {
        return ResponseEntity.ok(ResponseMessage.success(shopNoticeService.getNotice(noticeId)));
    }

    @ApiOperation("마켓 공지사항 상세 수정")
    @PutMapping("/{shopId}/notice/{noticeId}")
    public ResponseEntity<ResponseMessage> updateNotice(@ApiParam(value = "마켓 id") @PathVariable("shopId") long shopId, @ApiParam(value = "공지사항 id") @PathVariable("noticeId") long noticeId, @RequestBody @Valid NoticeRequestDto noticeRequestDto) {
        return ResponseEntity.ok(ResponseMessage.success(shopNoticeService.updateNotice(noticeId, noticeRequestDto)));
    }

    @ApiOperation("마켓 공지사항 삭제")
    @DeleteMapping("/{shopId}/notice/{noticeId}")
    public ResponseEntity<ResponseMessage> deleteNotice(@ApiParam(value = "마켓 id") @PathVariable("shopId") long shopId, @ApiParam(value = "공지사항 id") @PathVariable("noticeId") long noticeId) {
        shopNoticeService.deleteNotice(noticeId);
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
