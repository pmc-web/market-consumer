package com.pmc.market.service;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.TagRequestDto;
import com.pmc.market.model.dto.TagResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class TagServiceTest {
    @Autowired
    TagService tagService;

    @Transactional // for lazy initialize
    @DisplayName("전체 태그 목록 가져오기 - 20210531 통과")
    @Test
    void findAll() {
        List<TagResponseDto> tags = tagService.findAll();

        tags.stream().forEach(t -> {
            System.out.println(t.getShopIds());
        });
        assertTrue(tags.size() > 0);
    }

    @DisplayName("태그 추가 - 20210531 통과")
    @Test
    void makeTag() {
        TagRequestDto tagRequestDto = TagRequestDto.builder().name("태그테스트").build();
        tagService.makeTag(tagRequestDto);
    }

    @DisplayName("태그 삭제 - 20210531 통과")
    @Test
    void deleteTag() {
        long id = 6L;
        tagService.deleteTag(id);
    }

    @DisplayName("태그 검색 목록 - 20210531 통과")
    @Test
    void getTagsBySearch() {
        String searchWord = "태";
        List<TagResponseDto> tags = tagService.findByWord(searchWord);
        assertTrue(tags.size() > 0);
    }
}