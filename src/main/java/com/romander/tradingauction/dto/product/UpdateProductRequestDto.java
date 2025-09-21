package com.romander.tradingauction.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateProductRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String imageUrl;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    @NotNull
    private Boolean isForTrade;
    @NotNull
    private Boolean isForSale;
}
