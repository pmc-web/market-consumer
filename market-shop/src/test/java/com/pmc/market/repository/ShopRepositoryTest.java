package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.model.entity.Category;
import com.pmc.market.model.entity.Shop;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        ShopRequestDto shopRequestDto = ShopRequestDto.builder()
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
                .name(shopRequestDto.getName())
                .period(LocalDateTime.now().plusYears(shopRequestDto.getPeriod()))
                .fullDescription(shopRequestDto.getFullDescription())
                .shortDescription(shopRequestDto.getShortDescription())
                .regDate(LocalDateTime.now())
                .businessName(shopRequestDto.getBusinessName())
                .businessNumber(shopRequestDto.getBusinessNumber())
                .owner(shopRequestDto.getOwner())
                .telephone(shopRequestDto.getTelephone())
                .build();
        shopRepository.save(shop);

        assertEquals(shop.getId(), 1L); // auto-create 일 때

    }

    @DisplayName("신규 쇼핑몰 ")
    @Test
    void 신규_쇼핑몰_리스트() {
        int count = 6;
        Pageable pageable = PageRequest.of(0,count, Sort.by(Sort.Direction.ASC, "regDate"));
        Page<Shop> all = shopRepository.findAll(pageable);
        List<Shop> content = all.getContent();
        content.forEach(s->{
            System.out.println(s.getId()+" "+s.getRegDate());
        });
        assertEquals(count, content.size());
    }

    @Test
    void 카테고리별_쇼핑몰(){
        Category category = categoryRepository.findById(1L).get();
        List<Shop> shops = shopRepository.findByCategory(category);

        shops.forEach(s->{
            assertEquals(s.getCategory().getId(),1L);
        });
    }

    @Transactional
    @Test
    void 마켓조회_테스트(){
        List<Shop> shop = shopRepository.findAll();
        shop.forEach(s->s.getFavorites().forEach(f-> System.out.println(f.getId())));
    }

    @Transactional
    @Test
    void 마켓조회_테스트2_byId(){
        Long id = 1L;
        Shop shops = shopRepository.findById(id).get();
        shops.getFavorites().forEach(f-> System.out.println("favorite id"+f.getId()+" "));
    }
}