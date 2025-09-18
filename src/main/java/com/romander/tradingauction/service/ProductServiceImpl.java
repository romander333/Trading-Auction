package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.product.ProductRequestDto;
import com.romander.tradingauction.dto.product.ProductResponseDto;
import com.romander.tradingauction.dto.product.UpdateProductRequestDto;
import com.romander.tradingauction.exception.EntityNotFoundException;
import com.romander.tradingauction.mapper.ProductMapper;
import com.romander.tradingauction.model.Product;
import com.romander.tradingauction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductResponseDto> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        Product product = getProductById(id);
        return productMapper.toDto(product);
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = productMapper.toModel(productRequestDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, UpdateProductRequestDto productRequestDto) {
        Product product = getProductById(id);
        productMapper.updateModel(product, productRequestDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductResponseDto> getParticularUserProducts(Long userId, Pageable pageable) {
        return productRepository.findAllByUser_Id(userId, pageable)
                .map(productMapper::toDto);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by id: "
                        + id));
    }
}
