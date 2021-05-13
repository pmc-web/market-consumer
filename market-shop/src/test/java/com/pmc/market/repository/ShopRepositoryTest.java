package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.ShopInput;
import com.pmc.market.model.entity.Shop;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Test
    void 모든_쇼핑몰을_가져오기() throws Exception {
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
    void 쇼핑몰_생성() {
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

}