package com.pmc.market.repository;

import com.pmc.market.ShopApplication;
import com.pmc.market.domain.shop.entity.Category;
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
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("전체 카테고리 목록 가져오기")
    @Test
    void findAll() {
        List<Category> categories = categoryRepository.findAll();
        assertTrue(categories.size() > 0);
    }

    @DisplayName("카테고리 추가")
    @Test
    void makeCategory() {
        Category category = Category.builder()
                .mainCategory("핸드메이드 푸드")
                .subCategory("자몽청")
                .build();
        categoryRepository.save(category);

        assertTrue(category.getId() != 0);
    }

}