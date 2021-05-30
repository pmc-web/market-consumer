package com.pmc.market.service;

import com.pmc.market.model.dto.CategoryRequestDto;
import com.pmc.market.model.entity.Category;
import com.pmc.market.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void makeCategory(CategoryRequestDto categoryRequestDto) {
        categoryRepository.save(categoryRequestDto.toEntity());
    }
}
