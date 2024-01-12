package com.connector.dto;

import com.connector.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter
public class RegisterDto {
    @Schema(description = "유저 이름", example = "Eric")
    @Max(20)
    @NotBlank
    private String name;
    @Schema(description = "유저 이메일", example = "eric@naver.com")
    @Email
    private String email;
    @NotBlank
    private String password;

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
