package com.romander.tradingauction.dto.product;

import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateProductRequestDto {
    private String title;
    private String description;
    private String imageUrl;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    private Long categoryId;
    private Boolean isForTrade;
    private Boolean isForSale;
}
