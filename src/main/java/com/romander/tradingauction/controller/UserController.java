package com.romander.tradingauction.controller;

import com.romander.tradingauction.dto.user.AddressDto;
import com.romander.tradingauction.dto.user.RoleRequestDto;
import com.romander.tradingauction.dto.user.UpdateUserRequestDto;
import com.romander.tradingauction.dto.user.UserResponseDto;
import com.romander.tradingauction.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('MANAGER')")
    public void updateRole(@PathVariable Long id, @RequestBody RoleRequestDto requestDto) {
        userService.updateRole(requestDto, id);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public UserResponseDto updateProfile(@RequestBody @Valid UpdateUserRequestDto requestDto) {
        return userService.updateProfile(requestDto);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public UserResponseDto getProfile() {
        return userService.getMyProfileInfo();
    }

    @PutMapping("/me/address")
    @PreAuthorize("hasRole('CUSTOMER')")
    public UserResponseDto updateAddress(@RequestBody @Valid AddressDto requestDto) {
        return userService.updateMyAddress(requestDto);
    }
}
