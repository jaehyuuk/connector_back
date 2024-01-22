package com.connector.controller;

import com.connector.dto.*;
import com.connector.global.context.TokenContext;
import com.connector.global.context.TokenContextHolder;
import com.connector.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="프로필 API")
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    @Operation(summary = "프로필 전체 조회 API", description = "전체 프로필 정보를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProfileDto.class))
                    )
            }
    )
    public List<ProfileDto> getProfiles(

    ) {
        return profileService.getProfiles();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "프로필 상세 조회 API", description = "프로필의 상세 정보를 조회한다.")
    @Parameter(name = "userId", description = "유저 ID", in = ParameterIn.PATH)
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProfileDetailDto.class))
                    )
            }
    )
    public ProfileDetailDto getOneProfile(
            @PathVariable("userId") final Long userId
    ) {
        return profileService.getOneProfile(userId);
    }

    @GetMapping("/me")
    @Operation(summary = "본인 프로필 조회 API", description = "본인 프로필의 정보를 조회한다.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProfileDetailDto.class))
                    )
            }
    )
    public ProfileDetailDto getMyProfile() {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        return profileService.getOneProfile(userId);
    }

    @PostMapping
    @Operation(summary = "프로필 업데이트 API", description = "프로필을 업데이트한다.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UpsertProfileDto.class))
                    )
            }
    )
    public void upsertProfile(
            @RequestBody UpsertProfileDto profileDto
    ) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        profileService.upsertProfile(userId, profileDto);
    }

    @DeleteMapping
    @Operation(summary = "회원 탈퇴 API", description = "회원을 탈퇴한다.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProfileDto.class))
                    )
            }
    )
    public void deleteProfile() {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        profileService.deleteProfile(userId);
    }

    @PutMapping("/experience")
    @Operation(summary = "경력 추가 API", description = "경력을 추가 한다.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExperienceDto.class))
                    )
            }
    )
    public void addExperience(
            @RequestBody ExperienceDto experienceDto
    ) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        profileService.addExperience(userId, experienceDto);
    }

    @DeleteMapping("/experience/{experience_id}")
    @Operation(summary = "경력 삭제 API", description = "경력을 삭제한다.")
    @Parameter(name = "experience_id", description = "경력 ID", in = ParameterIn.PATH)
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExperienceDto.class))
                    )
            }
    )
    public void deleteExperience(
            @PathVariable("experience_id") final Long experienceId
    ) {
        profileService.deleteExperience(experienceId);
    }

    @PutMapping("/education")
    @Operation(summary = "학력 추가 API", description = "학력을 추가한다.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EducationDto.class))
                    )
            }
    )
    public void addEducation(
            @RequestBody EducationDto educationDto
    ) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        profileService.addEducation(userId, educationDto);
    }

    @DeleteMapping("/education/{education_id}")
    @Operation(summary = "학력 삭제 API", description = "학력을 삭제한다.")
    @Parameter(name = "education_id", description = "학력 ID", in = ParameterIn.PATH)
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EducationDto.class))
                    )
            }
    )
    public void deleteEducation(
            @PathVariable("education_id") final Long educationId
    ) {
        profileService.deleteEducation(educationId);
    }

    @GetMapping("/github/{github_id}")
    @Operation(summary = "GitHub 연결 API", description = "GitHub 레포지토리를 가져온다.")
    @Parameter(name = "github_id", description = "github ID", in = ParameterIn.PATH)
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GithubResponseItemDto.class))
                    )
            }
    )
    public List<GithubResponseItemDto> getGitRepositories(
            @PathVariable(value = "github_id") final String gitHubId
    ) {
        return profileService.getGitRepositories(gitHubId);
    }
}
