package com.romander.carsharing.service;

import com.romander.carsharing.dto.user.RoleRequestDto;
import com.romander.carsharing.dto.user.SignUpRequestDto;
import com.romander.carsharing.dto.user.UpdateUserRequestDto;
import com.romander.carsharing.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(SignUpRequestDto requestDto);

    void updateRole(RoleRequestDto requestDto, Long id);

    UserResponseDto getCurrentUserInfo();

    UserResponseDto updateProfile(UpdateUserRequestDto requestDto);
}
