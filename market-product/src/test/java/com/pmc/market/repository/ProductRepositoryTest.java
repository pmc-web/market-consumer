package com.pmc.market.repository;

import com.pmc.market.ProductApplication;
import com.pmc.market.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    public TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품_저장() throws Exception{
        Product product = Product.builder()
                .id(2L)
                .name("두번째상품")
                .price(200)
                .amount(20)
                .description("두번쨰상명")
                .build();
    }

    @Test
    void 상품_목록() throws Exception {
        List<Product> productList = productRepository.findAll();
        assertEquals(productList.size(), 1);
    }
}