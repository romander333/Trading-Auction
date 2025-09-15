package com.romander.carsharing.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SignInRequestDto {
    private String email;
    private String password;
}
