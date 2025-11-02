package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.product.ProductRequestDto;
import com.romander.tradingauction.dto.product.ProductResponseDto;
import com.romander.tradingauction.dto.product.UpdateProductRequestDto;
import com.romander.tradingauction.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponseDto> getProducts(Pageable pageable);

    ProductResponseDto getProduct(Long id);

    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto updateProduct(Long id, UpdateProductRequestDto productRequestDto);

    void deleteProduct(Long id);

    Page<ProductResponseDto> getParticularUserProducts(Long userId, Pageable pageable);

    Page<ProductResponseDto> getAllProductsByCategory(Long categoryId, Pageable pageable);
}
