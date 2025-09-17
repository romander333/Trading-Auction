package com.romander.tradingauction.service;

import com.romander.tradingauction.dto.user.AddressDto;
import com.romander.tradingauction.dto.user.RoleRequestDto;
import com.romander.tradingauction.dto.user.SignUpRequestDto;
import com.romander.tradingauction.dto.user.UpdateUserRequestDto;
import com.romander.tradingauction.dto.user.UserResponseDto;
import com.romander.tradingauction.exception.EntityNotFoundException;
import com.romander.tradingauction.exception.RegistrationException;
import com.romander.tradingauction.mapper.UserMapper;
import com.romander.tradingauction.model.Address;
import com.romander.tradingauction.model.Role;
import com.romander.tradingauction.model.User;
import com.romander.tradingauction.repository.RoleRepository;
import com.romander.tradingauction.repository.UserRepository;
import com.romander.tradingauction.security.AuthenticationService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final AuthenticationService authenticationService;

    @Override
    @Transactional
    public UserResponseDto register(SignUpRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("Email already exists");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Role role = roleRepository.findByName(Role.RoleName.CUSTOMER)
                .orElseThrow(() -> new EntityNotFoundException("Role not found by name"
                        + Role.RoleName.CUSTOMER));
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    @Transactional
    public void updateRole(RoleRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: "
                        + userId));

        Role role = roleRepository.findByName(requestDto.getRoleName())
                .orElseThrow(() -> new EntityNotFoundException("Can't find role by name: "
                        + requestDto.getRoleName()));
        user.setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);
    }

    @Override
    public UserResponseDto updateProfile(UpdateUserRequestDto requestDto) {
        User user = getCurrentUser();
        userMapper.updateUser(user, requestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserResponseDto getMyProfileInfo() {
        User user = getCurrentUser();
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateMyAddress(AddressDto requestDto) {
        User user = getCurrentUser();
        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
            user.setAddress(address);
        }
        address.setCity(requestDto.getCity());
        address.setCountry(requestDto.getCountry());
        address.setPostalCode(requestDto.getPostalCode());
        address.setStreet(requestDto.getStreet());
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    private User getCurrentUser() {
        return authenticationService.getCurrentUser();
    }
}
