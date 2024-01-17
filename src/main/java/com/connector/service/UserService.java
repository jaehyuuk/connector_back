package com.connector.service;

import com.connector.domain.User;
import com.connector.dto.*;
import com.connector.global.exception.BadRequestException;
import com.connector.global.token.TokenManager;
import com.connector.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenManager tokenManager;

    @Transactional
    public TokenResponseDto join(RegisterDto registerDto) {
        if (registerDto.getName().isEmpty() || registerDto.getPassword().isEmpty() || registerDto.getEmail().isEmpty()
        ) {
            throw new BadRequestException("Not empty");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {

            throw new BadRequestException("User already exists");
        }
        User user = userRepository.save(registerDto.toEntity());
        TokenDto tokenDto = TokenDto.builder().userId(user.getId()).build();

        return tokenManager.generateToken(tokenDto);
    }

    @Transactional(readOnly = true)
    public UserDto getAuth(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BadRequestException("Not User")
        );
        return UserDto.getUserDto(user);
    }

    @Transactional
    public TokenResponseDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                () -> new BadRequestException("Invalid Credentials")
        );
        user.checkPassword(loginDto.getPassword());

        TokenDto tokenDto = TokenDto.builder().userId(user.getId()).build();

        return tokenManager.generateToken(tokenDto);
    }
}
