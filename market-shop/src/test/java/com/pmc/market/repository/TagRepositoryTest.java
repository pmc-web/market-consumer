package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.domain.shop.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @DisplayName("전체 태그 목록 가져오기 - 20210531 통과")
    @Test
    void findAll() {
        List<Tag> tags = tagRepository.findAll();
        tags.stream().forEach(t -> {

        });
        assertTrue(tags.size() > 0);
    }

    @DisplayName("태그 추가 - 20210531 통과")
    @Test
    void makeCategory() {
        Tag tag = Tag.builder()
                .name("추가한태그")
                .build();
        tagRepository.save(tag);

        assertTrue(tag.getId() != 0);
    }

    @DisplayName("태그 삭제 - 20210531 통과")
    @Test
    void deleteTag() {
        long id = 5L;
        tagRepository.deleteById(id);
    }

    @DisplayName("태그 검색 목록 - 20210531 통과")
    @Test
    void getTagsBySearch() {
        String searchWord = "%태%";
        List<Tag> tags = tagRepository.findByNameLike(searchWord);
        assertTrue(tags.size() > 0);
    }

    @DisplayName("태그 이름으로 검색 - 20210601 통과")
    @Test
    void findByTagName() {
        String tagName = "태그1";
        List<Tag> tags = tagRepository.findTagByName(tagName);
        assertTrue(tags.size() > 0);
    }

}