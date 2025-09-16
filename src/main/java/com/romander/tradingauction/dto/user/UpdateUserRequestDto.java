package com.romander.tradingauction.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateUserRequestDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
