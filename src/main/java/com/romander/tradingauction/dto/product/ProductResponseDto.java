package com.romander.tradingauction.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.romander.tradingauction.dto.category.CategoryResponseDto;
import com.romander.tradingauction.model.Category;
import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private CategoryResponseDto category;
    private Boolean isForTrade;
    private Boolean isForSale;
    private LocalDateTime createdAt;
    private Long ownerId;
}
