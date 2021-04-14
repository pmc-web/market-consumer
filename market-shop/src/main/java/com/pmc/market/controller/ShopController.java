package com.pmc.market.controller;

import com.pmc.market.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ShopController {
    private final ShopService shopService;

}
