package com.romander.carsharing.dto.user;

import com.romander.carsharing.model.Role;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleRequestDto {
    private Role.RoleName roleName;
}
