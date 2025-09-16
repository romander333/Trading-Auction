package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.user.RoleRequestDto;
import com.romander.tradingauction.dto.user.SignUpRequestDto;
import com.romander.tradingauction.dto.user.UpdateUserRequestDto;
import com.romander.tradingauction.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(SignUpRequestDto requestDto);

    void updateRole(RoleRequestDto requestDto, Long id);

    UserResponseDto updateProfile(UpdateUserRequestDto requestDto);
}
