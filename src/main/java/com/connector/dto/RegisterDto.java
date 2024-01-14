package com.connector.dto;

import com.connector.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class RegisterDto {
    @Schema(description = "유저 이름", example = "Eric")
    private String name;
    @Schema(description = "유저 이메일", example = "eric@naver.com")
    @Email
    private String email;
    private String password;

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
