package com.pmc.market.repository;

import com.pmc.market.ProductApplication;
import com.pmc.market.model.product.entity.ProductFavorite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductFavoriteRepositoryTest {

    @Autowired
    ProductFavoriteRepository productFavoriteRepository;

    @DisplayName("조회")
    @Test
    void findByUserIdAndProductId() {
        Optional<ProductFavorite> favorite = productFavoriteRepository.findByUserIdAndProductId(1L, 1L);
        assertThat(favorite.get()).isNotNull();
    }
}