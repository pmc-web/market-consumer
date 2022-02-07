package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.domain.shop.entity.Category;
import com.pmc.market.domain.shop.entity.Shop;
import com.pmc.market.domain.shop.entity.ShopTag;
import com.pmc.market.domain.shop.entity.Tag;
import com.pmc.market.domain.shop.repository.CategoryRepository;
import com.pmc.market.domain.shop.repository.FavoriteRepository;
import com.pmc.market.domain.shop.repository.ShopRepository;
import com.pmc.market.model.dto.ShopRequestDto;
import com.pmc.market.model.dto.TagIdNameDto;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

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
                .businessName(shopRequestDto.getBusinessName())
                .businessNumber(shopRequestDto.getBusinessNumber())
                .owner(shopRequestDto.getOwner())
                .telephone(shopRequestDto.getTelephone())
                .build();
        shopRepository.save(shop);

        assertEquals(shop.getId(), 1L); // auto-create 일 때
    }

    @DisplayName("신규 쇼핑몰 - 최신순서로 쇼핑몰 전체 조회")
    @Test
    void 신규_쇼핑몰_리스트() {
        int count = 6;
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.ASC, "regDate"));
        Page<Shop> all = shopRepository.findAll(pageable); // select 쿼리 1번 사용 확인
        List<Shop> content = all.getContent();
        content.forEach(s -> {
            System.out.println(s.getId() + " " + s.getCreatedDate());
        });
        assertEquals(count, content.size());
    }

    @Test
    void 카테고리별_쇼핑몰() {
        List<Shop> shops = shopRepository.findByCategory_id(1L);
        shops.forEach(s -> {
            assertEquals(s.getCategory().getId(), 1L);
        });
    }

    @Transactional
    @Test
    void 마켓조회_테스트() {
        List<Shop> shop = shopRepository.findAll();
        shop.forEach(s -> s.getFavorites().forEach(f -> System.out.println(f.getId())));
    }

    @Transactional
    @Test
    void 마켓조회_테스트2() {
        List<Shop> shops = shopRepository.findAllShop();
        for (Shop shop : shops) {
            List<ShopTag> shopTags = shop.getShopTags();
            List<TagIdNameDto> map = shop.getShopTags().stream().map(shopTag -> TagIdNameDto.from(shopTag.getTag())).collect(Collectors.toList());
            List<Tag> tags = shopTags.stream().map(ShopTag::getTag).collect(Collectors.toList());
            map.forEach(t -> System.out.println(t.getId() + " " + t.getTagName()));
        }

    }

    @Transactional
    @Test
    void 마켓조회_테스트2_byId() {
        Long id = 1L;
        Shop shops = shopRepository.findById(id).get();
    }

    @DisplayName("마켓 조회 - 검색어")
    @Transactional
    @Test
    void getShopsBySearch() {
        String searchWord = "%켓%";
        List<Shop> result = shopRepository.findByNameLike(searchWord);
        assertTrue(result.size() > 0);
        result.forEach(s -> System.out.println(s.getName()));
    }

    @DisplayName("마켓 업데이트")
    @Test
    void updateShop() {
        long id = 6L;
        Shop shop = shopRepository.findById(id).get();
        long categoryId = 6L;
        String prevMainCategory = shop.getCategory().getMainCategory();
        Category category = categoryRepository.findById(categoryId).get();
        shop.updateCategory(category);
        shopRepository.save(shop);
        String updateMainCategory = shop.getCategory().getMainCategory();
        assertNotEquals(prevMainCategory, updateMainCategory);
    }

    @DisplayName("마켓 업데이트2")
    @Test
    void updateShop2() {
        Shop shop = shopRepository.findById(6L).get();
        String prev = shop.getName();
        shop.setName("업데이트 이름");
        shopRepository.save(shop);
        String now = shopRepository.findById(6L).get().getName();
        System.out.println(prev + " " + now);
        assertNotEquals(prev, now);
    }

    @DisplayName("마켓 삭제")
    @Test
    void 마켓_삭제() {
        shopRepository.deleteById(8L);
        // delete 3 : shop1+favorite 2
    }


}