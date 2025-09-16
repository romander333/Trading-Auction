package com.romander.tradingauction.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserResponseDto {
    private String email;
    private String firstName;
    private String lastName;
}
