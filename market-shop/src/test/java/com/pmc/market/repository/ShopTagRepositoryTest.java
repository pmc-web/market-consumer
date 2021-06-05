package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.ShopIdNameDto;
import com.pmc.market.model.dto.ShopTagResponseDto;
import com.pmc.market.model.entity.ShopTag;
import com.pmc.market.model.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class ShopTagRepositoryTest {

    @Autowired
    private ShopTagRepository shopTagRepository;

    @Test
    void findAll() {
        List<ShopTag> list = shopTagRepository.findAll();

        list.stream().forEach(t -> {
            System.out.println(t.getTag().getName() + " " + t.getShop().getId() + " ");
        });
    }

    @DisplayName("태그이름 검색 마켓 Id, Name 정보 포함 - 20210601 통과")
    @Test
    void findByTagName() {
        String tagName = "태그1";
        List<ShopTag> shopTags = shopTagRepository.findByTagName(tagName);
        Tag tag = shopTags.get(0).getTag();
        List<ShopIdNameDto> list = shopTags.stream().map(ShopIdNameDto::of).collect(Collectors.toList());
        ShopTagResponseDto tagShop = ShopTagResponseDto
                .builder()
                .tagId(tag.getId())
                .tagName(tag.getName())
                .shops(list)
                .build();
        ShopTagResponseDto tagShop2 = ShopTagResponseDto.of(shopTags);
        assertEquals(tagShop.getTagName(), tagShop2.getTagName());
        assertTrue(shopTags.size() > 0);
    }

    @DisplayName("태그이름 검색 2 마켓 Id, Name 정보 포함 - 20210601 통과")
    @Test
    void findByTagId() {
        long id = 1L;
        List<ShopTag> shopTags = shopTagRepository.findByTagId(id);
        ShopTagResponseDto tagShop = ShopTagResponseDto.of(shopTags);
        assertEquals(tagShop.getTagId(), id);
    }
}