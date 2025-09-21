package com.romander.tradingauction.mapper;

import com.romander.tradingauction.config.MapperConfig;
import com.romander.tradingauction.dto.product.ProductRequestDto;
import com.romander.tradingauction.dto.product.ProductResponseDto;
import com.romander.tradingauction.dto.product.UpdateProductRequestDto;
import com.romander.tradingauction.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {
    Product toModel(ProductRequestDto requestDto);

    @Mapping(target = "ownerId", source = "user.id")
    ProductResponseDto toDto(Product product);

    void updateModel(@MappingTarget Product product, UpdateProductRequestDto requestDto);
}
