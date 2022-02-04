package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/search")
@Api(tags = "검색 관련")
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "검색")
    @PostMapping
    public ResponseEntity<ResponseMessage> addSearchKeyword(@RequestParam("keyword") String keyword) {
        searchService.addSearchList(keyword);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation(value = "인기 검색어")
    @GetMapping("/popular")
    public ResponseEntity<ResponseMessage> getPopularSearchKeyword(
            @ApiParam(example = "7(일전)", value = "n일 전부터 검색") @RequestParam("daysAgo") long daysAgo,
            @ApiParam("인기검색어 갯수") @RequestParam("limit") int limit) {
        return ResponseEntity.ok(ResponseMessage.success(searchService.getPopularList(daysAgo, limit)));
    }
}
