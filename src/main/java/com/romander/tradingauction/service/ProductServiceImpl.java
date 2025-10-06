package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.product.ProductRequestDto;
import com.romander.tradingauction.dto.product.ProductResponseDto;
import com.romander.tradingauction.dto.product.UpdateProductRequestDto;
import com.romander.tradingauction.exception.AccessDeniedException;
import com.romander.tradingauction.exception.EntityNotFoundException;
import com.romander.tradingauction.mapper.ProductMapper;
import com.romander.tradingauction.model.Product;
import com.romander.tradingauction.model.User;
import com.romander.tradingauction.repository.ProductRepository;
import com.romander.tradingauction.security.AuthenticationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthenticationService authenticationService;

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
        User user = authenticationService.getCurrentUser();
        product.setCreatedAt(LocalDateTime.now());
        product.setUser(user);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, UpdateProductRequestDto productRequestDto) {
        User user = authenticationService.getCurrentUser();
        checkOwner(id, user.getId());
        Product product = getProductById(id);
        product.setCreatedAt(LocalDateTime.now());
        productMapper.updateModel(product, productRequestDto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Long userId = getCurrentUser().getId();
        checkOwner(id, userId);
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductResponseDto> getParticularUserProducts(Long userId, Pageable pageable) {
        return productRepository.findAllByUser_Id(userId, pageable)
                .map(productMapper::toDto);
    }

    private void checkOwner(Long userId, Long productId) {
        Product product = getProductById(productId);

        if (!product.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You can't update or delete another user's product");
        }
    }

    private User getCurrentUser() {
        return authenticationService.getCurrentUser();
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found by id: "
                        + id));
    }
}
