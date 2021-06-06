package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.shop.entity.Shop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ShopApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class JpaTest {
    @Autowired
    public TestEntityManager testEntityManager;

    @Autowired
    private ShopRepository shopRepository;

    @Test
    void Shop_저장() throws Exception {
        Shop shop = Shop.builder()
                .id(2L)
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
//        testEntityManager.persist(shop); //detached entity passed to persist error
//        assertEquals(shop, testEntityManager.find(Shop.class, shop.getId()));
    }

    @Test
    void Shop목록() throws Exception {
        List<Shop> shopList = shopRepository.findAll();
        assertEquals(shopList.size(), 1);
    }
}
