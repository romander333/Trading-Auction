package com.romander.tradingauction.mapper;

import com.romander.tradingauction.config.MapperConfig;
import com.romander.tradingauction.dto.category.CategoryRequestDto;
import com.romander.tradingauction.dto.category.CategoryResponseDto;
import com.romander.tradingauction.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    Category toModel(CategoryRequestDto requestDto);

    CategoryResponseDto toDto(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequestDto requestDto);
}
