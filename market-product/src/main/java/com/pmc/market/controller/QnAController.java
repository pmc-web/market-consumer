package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.QnARequestDto;
import com.pmc.market.service.QnAService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Product QnA Controller", tags = "상품 QnA관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/productQnA")
public class QnAController {
    private final QnAService qnAService;

    @ApiOperation("큐엔에이 작성 - 문의하기")
    @PostMapping
    public ResponseEntity<ResponseMessage> writeQnA(@RequestBody QnARequestDto qnARequestDto) {
        qnAService.makeQnA(qnARequestDto);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("큐엔에이 수정")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateQnA(@ApiParam("게시글 id") @PathVariable("id") long id, @RequestBody QnARequestDto qnARequestDto) {
        qnAService.update(id, qnARequestDto);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("문의글 단일조회")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getQnA(@ApiParam("게시글 id") @PathVariable("id") long id) {
        qnAService.getQnA(id);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("내가 쓴 문의글 보기")
    @GetMapping("/my")
    public ResponseEntity<ResponseMessage> getMyQnAList() {
        return ResponseEntity.ok(ResponseMessage.success(qnAService.getUserQnAList()));
    }

    @ApiOperation("상품 문의글 보기")
    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseMessage> getProductQnAList(@PathVariable("id") long id) {
        return ResponseEntity.ok(ResponseMessage.success(qnAService.getProductQnAList(id)));
    }

    @ApiOperation("마켓 전체 상품 문의글 보기")
    @GetMapping("/shop/{id}")
    public ResponseEntity<ResponseMessage> getShopQnAList(@PathVariable("id") long id) {
        return ResponseEntity.ok(ResponseMessage.success(qnAService.getShopQnAList(id)));
    }

    @ApiOperation("문의글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteQnA(@PathVariable("id") long id) {
        qnAService.delete(id);
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
