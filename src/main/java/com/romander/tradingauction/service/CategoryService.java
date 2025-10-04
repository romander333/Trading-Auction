package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.category.CategoryRequestDto;
import com.romander.tradingauction.dto.category.CategoryResponseDto;
import java.util.List;

public interface CategoryService {
    public List<CategoryResponseDto> getAllCategories();

    public CategoryResponseDto getCategoryById(Long id);

    public CategoryResponseDto createCategory(CategoryRequestDto category);

    public CategoryResponseDto updateCategory(CategoryRequestDto category, Long id);

    public void deleteCategory(Long id);
}
