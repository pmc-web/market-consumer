package com.pmc.market.service;

import com.pmc.market.ShopApplication;
import com.pmc.market.model.dto.CategoryRequestDto;
import com.pmc.market.model.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ShopApplication.class})
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @DisplayName("전체 카테고리 목록 가져오기")
    @Test
    void findAll() {
        List<Category> categories = categoryService.findAll();
        assertTrue(categories.size() > 0);
    }

    @DisplayName("카테고리 추가")
    @Test
    void makeCategory() {
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .mainCategory("핸드메이드 디저트")
                .subCategory("레몬청")
                .build();
        categoryService.makeCategory(categoryRequestDto);
    }
}