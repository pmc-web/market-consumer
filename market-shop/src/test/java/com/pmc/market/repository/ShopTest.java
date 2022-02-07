package com.pmc.market.repository;

import com.pmc.market.domain.shop.entity.Category;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.user.entity.User;

import java.time.LocalDateTime;

class ShopTest {
    static Shop createTestShop(User testUser, Category testCategory) {
        return Shop.builder()
                .name("test shop")
                .businessName("test business name")
                .businessNumber("test business number")
                .category(testCategory)
                .deliveryCost(0)
                .fullDescription("full description")
                .shortDescription("short")
                .owner("owner")
                .user(testUser)
                .telephone("0000-1111-2222")
                .period(LocalDateTime.now().plusYears(1))
                .qnaDescription("qnA")
                .shipDescription("ship")
                .build();
    }
}