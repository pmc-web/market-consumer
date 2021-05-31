package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.TagRequestDto;
import com.pmc.market.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Tag Controller", tags = "태그 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @ApiOperation("전체 태그 리스트")
    @GetMapping
    public ResponseEntity<?> getAllTags() {
        return ResponseEntity.ok(ResponseMessage.success(tagService.findAll()));
    }

    @ApiOperation("태그 추가")
    @PostMapping
    public ResponseEntity<?> makeTag(@RequestBody @Valid TagRequestDto tagRequestDto) {
        tagService.makeTag(tagRequestDto);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("태그 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("태그 검색")
    @GetMapping("/search")
    public ResponseEntity<?> getTagsBySearch(@RequestParam @ApiParam("검색할 단어") String searchWord) {
        return ResponseEntity.ok(ResponseMessage.success(tagService.findByWord(searchWord)));
    }
}
