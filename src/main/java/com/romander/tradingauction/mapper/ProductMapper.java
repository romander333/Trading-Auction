package com.romander.tradingauction.mapper;

import com.romander.tradingauction.config.MapperConfig;
import com.romander.tradingauction.dto.product.ProductRequestDto;
import com.romander.tradingauction.dto.product.ProductResponseDto;
import com.romander.tradingauction.dto.product.UpdateProductRequestDto;
import com.romander.tradingauction.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class,
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "category.id", source = "categoryId")
    Product toModel(ProductRequestDto requestDto);

    @Mapping(target = "ownerId", source = "user.id")
    ProductResponseDto toDto(Product product);

    void updateModel(@MappingTarget Product product, UpdateProductRequestDto requestDto);
}
