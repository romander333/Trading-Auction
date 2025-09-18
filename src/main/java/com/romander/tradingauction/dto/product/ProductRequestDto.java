package com.romander.tradingauction.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;
    private BigDecimal price;
    @NotEmpty
    private boolean isForTrade;
    @NotEmpty
    private boolean isForSales;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private Long ownerId;
}
