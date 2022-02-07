package com.pmc.market.service;

import com.pmc.market.domain.shop.repository.CategoryRepository;
import com.pmc.market.model.dto.CategoryDto;
import com.pmc.market.model.dto.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(CategoryDto::from).collect(Collectors.toList());
    }

    @Override
    public void makeCategory(CategoryRequestDto categoryRequestDto) {
        categoryRepository.save(categoryRequestDto.toEntity());
    }
}
