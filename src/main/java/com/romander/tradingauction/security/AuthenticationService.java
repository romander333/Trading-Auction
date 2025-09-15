package com.romander.carsharing.security;

import com.romander.carsharing.dto.user.SignInRequestDto;
import com.romander.carsharing.dto.user.SignInResponseDto;
import com.romander.carsharing.exception.UserNotAuthenticatedException;
import com.romander.carsharing.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;

    public SignInResponseDto authenticate(SignInRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        String token = jwtCore.generateToken(authentication.getName());
        return new SignInResponseDto(token);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNotAuthenticatedException("Cannot get current user");
        }
        return (User) authentication.getPrincipal();
    }
}
