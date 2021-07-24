package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.model.dto.ReviewRequestDto;
import com.pmc.market.model.user.entity.User;
import com.pmc.market.security.auth.CustomUserDetails;
import com.pmc.market.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(value = "Review Controller", tags = "상품 리뷰 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @ApiOperation("리뷰쓰기")
    @PostMapping
    public ResponseEntity<ResponseMessage> writeReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto, @RequestParam("files") MultipartFile[] files) {
        reviewService.makeReview(reviewRequestDto, files);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("리뷰 수정")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ResponseMessage> updateReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto, @PathVariable("reviewId") long reviewId) {
        reviewService.updateReview(reviewRequestDto, reviewId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("내가쓴 리뷰 보기")
    @GetMapping("/my")
    public ResponseEntity<ResponseMessage> getMyReviews() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        reviewService.getMyReviews(user);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("상품 전체 리뷰 보기")
    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseMessage> getProductReviews(@PathVariable("productId") long productId) {
        reviewService.getProductReviews(productId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("마켓의 상품 리뷰 전체 보기")
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<ResponseMessage> getShopReviews(@PathVariable("shopId") long shopId) {
        reviewService.getShopReviews(shopId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @ApiOperation("리뷰 상세보기")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ResponseMessage> getReviewDetail(@PathVariable("reviewId") long reviewId) {
        return ResponseEntity.ok(ResponseMessage.success(reviewService.getReviewDetail(reviewId)));
    }

    @ApiOperation("리뷰 삭제")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ResponseMessage> deleteReview(@PathVariable("reviewId") long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(ResponseMessage.success());
    }

}
