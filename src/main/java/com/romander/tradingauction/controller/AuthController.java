package com.romander.tradingauction.controller;

import com.romander.tradingauction.dto.user.SignInRequestDto;
import com.romander.tradingauction.dto.user.SignInResponseDto;
import com.romander.tradingauction.dto.user.SignUpRequestDto;
import com.romander.tradingauction.dto.user.UserResponseDto;
import com.romander.tradingauction.security.AuthenticationService;
import com.romander.tradingauction.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto register(@RequestBody @Valid SignUpRequestDto requestDto) {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public SignInResponseDto login(@RequestBody @Valid SignInRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
