package com.romander.tradingauction.dto.user;

import com.romander.tradingauction.model.Address;
import com.romander.tradingauction.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Data
@Password
@Accessors(chain = true)
public class SignUpRequestDto {
    @Email
    @NotBlank
    private String email;
    @Length(min = 8, max = 16)
    private String password;
    @Length(min = 8, max = 16)
    private String confirmPassword;
    @NotBlank
    private String phone;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private Address address;
}
