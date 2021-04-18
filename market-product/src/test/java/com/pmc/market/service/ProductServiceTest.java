package com.pmc.market.service;

import com.pmc.market.ProductApplication;
import com.pmc.market.entity.Product;
import com.pmc.market.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductApplication.class})
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품_전체_가져오기() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .name("첫번째상품")
                .price(100)
                .amount(10)
                .description("첫번째상품명")
                .build();

        productRepository.save(product);

        List<Product> result = productRepository.findAll();
        assertEquals(product.getId(), result.get(0).getId());

    }
}