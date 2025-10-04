package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.product.ProductRequestDto;
import com.romander.tradingauction.dto.product.ProductResponseDto;
import com.romander.tradingauction.dto.product.UpdateProductRequestDto;
import com.romander.tradingauction.exception.AccessDeniedException;
import com.romander.tradingauction.exception.EntityNotFoundException;
import com.romander.tradingauction.mapper.ProductMapper;
import com.romander.tradingauction.model.Category;
import com.romander.tradingauction.model.Product;
import com.romander.tradingauction.model.User;
import com.romander.tradingauction.repository.CategoryRepository;
import com.romander.tradingauction.repository.ProductRepository;
import com.romander.tradingauction.security.AuthenticationService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthenticationService authenticationService;
    private final CategoryRepository categoryRepository;

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
        Category category = getCategoryById(productRequestDto.getCategoryId());
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setUser(user);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductResponseDto updateProduct(Long id, UpdateProductRequestDto requestDto) {
        User user = authenticationService.getCurrentUser();
        checkOwner(user.getId(), id);
        Product product = getProductById(id);

        if (requestDto.getCategoryId() != null) {
            Category category = getCategoryById(requestDto.getCategoryId());
            product.setCategory(category);
        }

        product.setCreatedAt(LocalDateTime.now());
        productMapper.updateModel(product, requestDto);
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

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found by id: " + id));
    }
}
