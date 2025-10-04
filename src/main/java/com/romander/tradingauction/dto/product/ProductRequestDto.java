package com.romander.tradingauction.dto.product;

import com.romander.tradingauction.validation.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @Image
    private String imageUrl;
    private BigDecimal price;
    @NotNull
    private Boolean isForTrade;
    @NotNull
    private Boolean isForSale;
}
