package com.pmc.market.service;

import com.pmc.market.ProductApplication;
import com.pmc.market.entity.Attachment;
import com.pmc.market.entity.Product;
import com.pmc.market.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductApplication.class})
@Transactional
class ProductServiceImplTest {

    @Autowired ProductService productService;
    @Autowired ProductRepository productRepository;
    @Autowired EntityManager em;

    @Test
    public void 상품_등록() throws Exception {
        //given 이런게 주어졌을 때
        Product products = Product.builder()
                .id(2L)
                .name("목걸이")
                .price(2000)
                .amount(20)
                .description("목걸이입니다.")
                .build();

        //when 이렇게 하면
        productRepository.save(products);
        em.flush();

        //then 이렇게 된다.

    }

    @Test
    public void 상품_단건() throws Exception {
        //given 이런게 주어졌을 때
        Product products = Product.builder()
                .id(2L)
                .build();

        //when 이렇게 하면
        Product result = productRepository.findOne(products.getId());
        em.flush();
        //then 이렇게 된다.
        assertEquals(products.getId(), result.getId());
    }

    @Test
    public void 상품_복수건() throws Exception {
        //given 이런게 주어졌을 때

        //when 이렇게 하면
        List<Product> result = productRepository.findAll();
        em.flush();
        //then 이렇게 된다.
        assertEquals(1, result.size());
    }
}