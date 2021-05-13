package com.pmc.market.controller;

import com.pmc.market.entity.Product;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Product Controller", tags = "상품 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ApiOperation("상품 등록")
    public ResponseEntity<?> saveProduct(@RequestBody @Valid Product product){ // @RequestBody HTTP 요청몸체를 자바객체로 전달받음
        productService.saveProduct(product);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @GetMapping
    @ApiOperation("상품 목록")
    public ResponseEntity<?> findProducts(
            @ApiParam(name="keyword", value="상품 검색 키워드") @RequestParam(required = false) String keyword){
        return ResponseEntity.ok(ResponseMessage.success(productService.findProducts()));
    }

    @GetMapping("{productId}")
    @ApiOperation("특정 상품 조회")
    public ResponseEntity<?> findOneProduct(@PathVariable Long productId){
        return ResponseEntity.ok(ResponseMessage.success(productService.findOneProduct(productId)));
    }

    @GetMapping("/popular")
    @ApiOperation("오늘의 인기 상품")
    public ResponseEntity<?> findOneProduct(
            @ApiParam(name="limit", value = "검색할 인기 상품 갯수") @RequestParam(required = false) Integer limit){
        return ResponseEntity.ok(ResponseMessage.success(productService.todayPopularProducts()));
    }

}
