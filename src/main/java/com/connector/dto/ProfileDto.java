package com.connector.dto;

import com.connector.domain.Profile;
import com.connector.domain.Skill;
import com.connector.domain.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProfileDto {
    private User user;
    private String company;
    private String location;
    private String bio;
    private List<String> skills;

    public ProfileDto(Profile profile) {
        user = profile.getUser();
        company = profile.getExperiences().isEmpty() ? "" : profile.getExperiences().get(0).getCompany();
        location = profile.getLocation();
        bio = profile.getBio();
        skills = profile.getSkills().stream().map(Skill::getName).collect(Collectors.toList());
    }
}
