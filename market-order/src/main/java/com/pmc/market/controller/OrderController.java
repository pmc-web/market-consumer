package com.pmc.market.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Order Controller", tags = "주문 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
}
