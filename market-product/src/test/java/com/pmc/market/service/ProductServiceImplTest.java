package com.pmc.market.service;

import com.pmc.market.ProductApplication;
import com.pmc.market.model.PageRequest;
import com.pmc.market.domain.product.vo.ProductVo;
import com.pmc.market.domain.user.entity.User;
import com.pmc.market.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceImplTest {

    @Autowired
    ProductService productService;

    @Autowired
    UserRepository userRepository;

    @Test
    void likeUpdateProduct() {
        User user = userRepository.findById(1L).get();
        productService.likeUpdateProduct(2L, user);
    }

    @Test
    void getTodayPopularProducts() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setSize(2);
        List<ProductVo> result = productService.getTodayPopularProducts(pageRequest);

        assertThat(result.size()).isGreaterThan(0);
    }
}