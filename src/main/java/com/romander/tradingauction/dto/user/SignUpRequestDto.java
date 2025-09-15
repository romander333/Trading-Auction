package com.romander.carsharing.dto.user;

import com.romander.carsharing.validation.Password;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Password
@Accessors(chain = true)
public class SignUpRequestDto {
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
}
