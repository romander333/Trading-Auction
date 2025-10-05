package com.romander.tradingauction.controller;

import com.romander.tradingauction.dto.category.CategoryRequestDto;
import com.romander.tradingauction.dto.category.CategoryResponseDto;
import com.romander.tradingauction.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryResponseDto createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return categoryService.createCategory(categoryRequestDto);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public CategoryResponseDto getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public CategoryResponseDto updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequestDto requestDto) {
        return categoryService.updateCategory(requestDto, id);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
