package com.pmc.market.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Shop Controller", tags = "마켓 관련")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shops")
public class ShopNoticeController {

}
