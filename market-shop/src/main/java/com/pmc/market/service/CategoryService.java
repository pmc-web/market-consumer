package com.pmc.market.service;

import com.pmc.market.model.dto.CategoryRequestDto;
import com.pmc.market.model.shop.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    void makeCategory(CategoryRequestDto categoryRequestDto);
}
