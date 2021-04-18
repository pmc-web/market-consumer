package com.pmc.market.controller;

import com.pmc.market.model.ResponseMessage;
import com.pmc.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllShops(){
        return ResponseEntity.ok(ResponseMessage.success(productService.findAll()));
    }
}
