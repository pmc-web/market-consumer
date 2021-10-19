package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.CategoryRequestDto;
import com.pmc.market.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Category Controller", tags = "카테고리 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation("전체 카테고리 리스트")
    @GetMapping
    public ResponseEntity<ResponseMessage> getAllCategories() {
        return ResponseEntity.ok(ResponseMessage.success(categoryService.findAll()));
    }

    @ApiOperation("카테고리 추가")
    @PostMapping
    public ResponseEntity<ResponseMessage> makeCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryService.makeCategory(categoryRequestDto);
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
