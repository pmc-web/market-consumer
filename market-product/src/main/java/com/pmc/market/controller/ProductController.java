package com.pmc.market.controller;

import com.pmc.market.entity.Product;
import com.pmc.market.model.ResponseMessage;
import com.pmc.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody @Valid Product product){ // @RequestBody HTTP 요청몸체를 자바객체로 전달받음
        productService.saveProduct(product);
        return ResponseEntity.ok(ResponseMessage.success());
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findProducts(){
        return ResponseEntity.ok(ResponseMessage.success(productService.findProducts()));
    }

    @GetMapping("/findOne")
    public ResponseEntity<?> findOneProduct(Long productId){
        return ResponseEntity.ok(ResponseMessage.success(productService.findOneProduct(productId)));
    }
}
