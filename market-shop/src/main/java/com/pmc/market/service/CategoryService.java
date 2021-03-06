package com.pmc.market.service;

import com.pmc.market.model.dto.CategoryDto;
import com.pmc.market.model.dto.CategoryRequestDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();

    void makeCategory(CategoryRequestDto categoryRequestDto);
}
