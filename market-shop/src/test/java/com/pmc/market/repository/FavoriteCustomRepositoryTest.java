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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        /*
        다음 Shop 은 NotNull 제약 조건 을 전부 없애야 테스트 가능
        Shop shop = Shop.builder()
                .id(1L)
                .name("shop1")
                .build();
        shopRepository.save(shop);
        Shop shop2 = Shop.builder()
                .id(2L)
                .name("shop2")
                .build();
        shopRepository.save(shop2);
        Shop shop3 = Shop.builder()
                .id(3L)
                .name("shop3")
                .build();
        shopRepository.save(shop3);
        Shop shop4 = Shop.builder()
                .id(4L)
                .name("shop3")
                .build();
        shopRepository.save(shop4);
        User user = User.builder()
                .id(1L)
                .email("annna0449@naver.com")
                .password("password123$")
                .role(Role.BUYER)
                .build();
        userRepository.save(user);
        Favorite f1 = Favorite.builder()
                .id(1L)
                .shop(shop)
                .user(user)
                .build();
        favoriteRepository.save(f1);
        Favorite f2 = Favorite.builder()
                .id(2L)
                .shop(shop2)
                .user(user)
                .build();
        favoriteRepository.save(f2);
        Favorite f3 = Favorite.builder()
                .id(3L)
                .shop(shop3)
                .user(user)
                .build();
        favoriteRepository.save(f3);
        Favorite f4 = Favorite.builder()
                .id(4L)
                .shop(shop3)
                .user(user)
                .build();
        favoriteRepository.save(f4);
        Favorite f5 = Favorite.builder()
                .id(5L)
                .shop(shop4)
                .user(user)
                .build();
        favoriteRepository.save(f5);
         */
        List<ShopResponseDto> shopIds = favoriteCustomRepository.findShopsMostFavoriteCount(3);
        assertEquals(shopIds.size(), 3);
    }

}