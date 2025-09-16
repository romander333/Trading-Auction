package com.romander.tradingauction.dto.user;

import com.romander.tradingauction.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleRequestDto {
    @NotNull(message = "roleName cannot be null")
    private Role.RoleName roleName;
}
