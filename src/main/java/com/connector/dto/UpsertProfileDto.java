package com.connector.dto;

import com.connector.domain.Profile;
import com.connector.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class UpsertProfileDto {
    @Schema(description = "회사명", example = "SK Hynix")
    private String company;
    @Schema(description = "사는 곳", example = "Bucheon")
    private String location;
    @Schema(description = "현재 상태", example = "Developer")
    private String status;
    @Schema(description = "자기소개", example = "안녕하세요")
    private String bio;
    @Schema(description = "웹사이트", example = "https://www.naver.com")
    private String website;
    @Schema(description = "기술 스택")
    private String skills;

    public Profile toEntity(User user) {
        return Profile.builder()
                .user(user)
                .company(company)
                .location(location)
                .status(status)
                .bio(bio)
                .website(website)
                .skills(new ArrayList<>())
                .experiences(new ArrayList<>())
                .educations(new ArrayList<>())
                .build();
    }
}