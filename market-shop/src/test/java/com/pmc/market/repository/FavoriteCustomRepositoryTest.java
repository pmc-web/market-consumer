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

import javax.persistence.EntityManager;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class FavoriteCustomRepositoryTest {
    @Autowired
    private FavoriteCustomRepository favoriteCustomRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void jpaTest() {
        /*
           SELECT sf.likes, s.*  FROM shop s left join
           (select count(shop_id) as likes, f.shop_id from favorite f group by f.shop_id) sf
           on sf.shop_id = s.id
         */
        /*
            select s.*, f.likes from (SELECT count(*) as likes, shop_id
            FROM market.favorite group by shop_id order by likes desc)f
            inner join shop s on s.id = f.shop_id;
        */

    }

    @DisplayName("인기순 n개 - 쿼리 1개 사용 확인")
    @Test
    @Rollback
    void 쇼핑몰_리스트_favorite_table() {
        List<ShopResponseDto> shopIds = favoriteCustomRepository.findShopsMostFavoriteCount(2, 2);
        shopIds.stream().forEach(s -> System.out.println(s.getId() + " " + s.getLikes()));
    }

}