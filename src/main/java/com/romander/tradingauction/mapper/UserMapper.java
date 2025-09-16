package com.romander.tradingauction.mapper;

import com.romander.tradingauction.config.MapperConfig;
import com.romander.tradingauction.dto.user.SignUpRequestDto;
import com.romander.tradingauction.dto.user.UpdateUserRequestDto;
import com.romander.tradingauction.dto.user.UserResponseDto;
import com.romander.tradingauction.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    void updateUser(@MappingTarget User user, UpdateUserRequestDto requestDto);

    User toModel(SignUpRequestDto requestDto);

    UserResponseDto toDto(User user);
}
