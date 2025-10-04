package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.category.CategoryRequestDto;
import com.romander.tradingauction.dto.category.CategoryResponseDto;
import com.romander.tradingauction.exception.EntityNotFoundException;
import com.romander.tradingauction.mapper.CategoryMapper;
import com.romander.tradingauction.model.Category;
import com.romander.tradingauction.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        return categoryMapper.toDto(getCategory(id));
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto) {
        Category category = categoryMapper.toModel(requestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryResponseDto updateCategory(CategoryRequestDto requestDto, Long id) {
        Category category = getCategory(id);
        categoryMapper.updateCategory(category, requestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category not found by id : " + id));
    }
}
