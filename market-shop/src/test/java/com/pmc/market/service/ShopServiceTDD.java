package com.pmc.market.service;

import com.pmc.market.ShopApplication;
import com.pmc.market.entity.Shop;
import com.pmc.market.repository.ShopRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class ShopServiceTDD {

    @Autowired
    private ShopRepository shopRepository;

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
}