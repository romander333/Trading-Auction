package com.romander.carsharing.mapper;

import com.romander.carsharing.config.MapperConfig;
import com.romander.carsharing.dto.user.SignUpRequestDto;
import com.romander.carsharing.dto.user.UpdateUserRequestDto;
import com.romander.carsharing.dto.user.UserResponseDto;
import com.romander.carsharing.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    void updateUser(@MappingTarget User user, UpdateUserRequestDto requestDto);

    User toModel(SignUpRequestDto requestDto);

    UserResponseDto toDto(User user);
}
