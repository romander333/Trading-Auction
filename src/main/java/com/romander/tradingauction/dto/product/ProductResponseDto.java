package com.romander.tradingauction.dto.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private boolean isForTrade;
    private boolean isForSales;
    private LocalDateTime createdAt;
    private Long ownerId;
}
