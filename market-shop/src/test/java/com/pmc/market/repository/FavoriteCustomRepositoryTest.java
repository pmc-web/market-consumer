package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.ShopResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class FavoriteCustomRepositoryTest {
    @Autowired
    private FavoriteCustomRepository favoriteCustomRepository;

    @DisplayName("인기순 n개 - 쿼리 1개 사용 확인")
    @Test
    @Rollback
    void 쇼핑몰_리스트_favorite_table() {
        List<ShopResponseDto> shopIds = favoriteCustomRepository.findShopsMostFavoriteCount(2, 2);
        shopIds.stream().forEach(s -> System.out.println(s.getId() + " " + s.getLikes()));
    }

}