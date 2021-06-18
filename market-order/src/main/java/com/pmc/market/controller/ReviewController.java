package com.pmc.market.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Review Controller", tags = "상품 리뷰 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders/review")
public class ReviewController {
}
