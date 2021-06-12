package com.pmc.market.controller;

import com.pmc.market.annotation.PageableParams;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.product.vo.ProductCreateParamVo;
import com.pmc.market.model.product.vo.ProductUpdateParamVo;
import com.pmc.market.model.product.vo.SearchProductParam;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "Product Controller", tags = "상품 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ApiOperation("상품 등록")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreateParamVo param) {
        return ResponseEntity.ok(ResponseMessage.success(productService.create(param)));
    }

    @PutMapping("/{productId}")
    @ApiOperation("상품 업데이트")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductUpdateParamVo param) {
        param.setId(productId);
        return ResponseEntity.ok(ResponseMessage.success(productService.update(param)));
    }

    @GetMapping
    @ApiOperation("상품 목록")
    @PageableParams
    public ResponseEntity<?> findProducts(
            @ApiParam(name = "keyword", value = "상품 검색 키워드") @RequestParam(required = false) SearchProductParam searchParam
            , @PageableDefault @ApiIgnore Pageable pageable) {
        return ResponseEntity.ok(ResponseMessage.success(productService.get(searchParam, pageable)));
    }

    @GetMapping("/{productId}")
    @ApiOperation("특정 상품 조회")
    public ResponseEntity<?> findOneProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(ResponseMessage.success(productService.getById(productId)));
    }

    @GetMapping("/popular")
    @ApiOperation("오늘의 인기 상품")
    @PageableParams
    public ResponseEntity<?> findOneProduct(@PageableDefault @ApiIgnore Pageable pageable) {
        return ResponseEntity.ok(ResponseMessage.success(productService.getTodayPopularProducts(pageable)));
    }

    @PutMapping("/{productId}/like")
    @ApiOperation("상품 좋아요")
    public ResponseEntity<?> likeProduct(@PathVariable Long productId, @AuthenticationPrincipal @ApiIgnore CustomUserDetails user) {
        // FIXME
        return ResponseEntity.ok(ResponseMessage.success());
    }
}
