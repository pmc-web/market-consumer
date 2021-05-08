package com.pmc.market.service;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.FavoriteShopId;
import com.pmc.market.model.entity.Favorite;
import com.pmc.market.model.entity.Role;
import com.pmc.market.model.entity.Shop;
import com.pmc.market.model.dto.ShopInput;
import com.pmc.market.model.entity.User;
import com.pmc.market.repository.FavoriteCustomRepository;
import com.pmc.market.repository.FavoriteRepository;
import com.pmc.market.repository.ShopRepository;
import com.pmc.market.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class ShopServiceTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private FavoriteCustomRepository favoriteCustomRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopService shopService;

    @Test
    void 모든_쇼핑몰을_가져오기() throws Exception{
        Shop shop = Shop.builder()
                .id(1L)
                .name("쇼핑몰1")
                .telephone("010-0000-0000")
                .businessName("쇼핑몰1")
                .fullDescription("쇼핑몰 설명")
                .owner("주인")
                .shortDescription("악세사리 쇼핑몰")
                .regDate(LocalDateTime.now())
                .period(LocalDateTime.now().plusYears(1))
                .businessNumber("00-000-000")
                .build();

        shopRepository.save(shop);

        List<Shop> result = shopRepository.findAll();

        assertEquals(shop.getId(), result.get(0).getId());
    }

    @DisplayName("makeShop() 테스트")
    @Test
    void 쇼핑몰_생성(){
        ShopInput shopInput = ShopInput.builder()
                .name("쇼핑몰1")
                .telephone("010-0000-0000")
                .businessName("쇼핑몰1")
                .fullDescription("쇼핑몰 설명")
                .owner("주인")
                .shortDescription("악세사리 쇼핑몰")
                .period(1) // 유지기간 1년
                .businessNumber("00-000-000")
                .build();
        Shop shop = Shop.builder()
                .name(shopInput.getName())
                .period(LocalDateTime.now().plusYears(shopInput.getPeriod()))
                .fullDescription(shopInput.getFullDescription())
                .shortDescription(shopInput.getShortDescription())
                .regDate(LocalDateTime.now())
                .businessName(shopInput.getBusinessName())
                .businessNumber(shopInput.getBusinessNumber())
                .owner(shopInput.getOwner())
                .telephone(shopInput.getTelephone())
                .build();
        shopRepository.save(shop);

        assertEquals(shop.getId(), 1L); // auto-create 일 때

    }

    @DisplayName("인기순 n개")
    @Test
    void 쇼핑몰_리스트_favorite_table(){
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
        List<FavoriteShopId> shopIds = favoriteCustomRepository.findShopsMostFavoriteCount(3);
        assertEquals(shopIds.size(), 3);

        List<Shop> shops = new ArrayList<>();
        shopIds.forEach(id -> {
//            shops.add(shopRepository.findById(Long.valueOf(id)).get());
        });

        shops.forEach(s-> System.out.println(s.getId()));
//        shopRepository.findAllById(shopIds);
//        List<Shop> shops = shopService.findFavorite(3);
//        assertEquals(shops.size(), 3);

    }
}