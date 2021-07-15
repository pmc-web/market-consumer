package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.error.exception.EntityNotFoundException;
import com.pmc.market.model.shop.entity.Favorite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class FavoriteRepositoryTest {

    @Autowired
    FavoriteRepository favoriteRepository;

    @DisplayName("좋아요")
    @Test
    void findFavorite() {
        Favorite favorite = favoriteRepository.findByUserIdAndShopId(1L, 1L)
                .orElseThrow(() -> new EntityNotFoundException("좋아요한 항목이 없습니다."));
        assertThat(favorite).isNotNull();
    }

    @Test
    void findShopMostFavoriteCount() {
        Page<Object[]> result = favoriteRepository.findShopMostFavoriteCount(PageRequest.of(0, 10));
        assertThat(result).hasSizeGreaterThan(0);
    }
}