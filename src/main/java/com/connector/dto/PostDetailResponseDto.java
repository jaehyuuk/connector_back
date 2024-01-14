package com.connector.dto;

import com.connector.domain.Comment;
import com.connector.repository.model.GetPostResponseModel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResponseDto {
    private Long id;
    private String text;
    private String name;
    private String avatar;
    private Long userId;
    private List<CommentResponseDto> comments;
    private LocalDateTime date;

    @Builder
    public PostDetailResponseDto(Long id, String text, String name, String avatar, Long userId, List<CommentResponseDto> comments, LocalDateTime date) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
        this.comments = comments;
        this.date = date;
    }

    public static PostDetailResponseDto of(GetPostResponseModel model) {
        return PostDetailResponseDto.builder()
                .id(model.getPost().getId())
                .text(model.getPost().getContent())
                .name(model.getUser().getName())
                .avatar(model.getUser().getAvatar())
                .userId(model.getUser().getId())
                .comments(model.getPost().getComments().stream()
                        .map(CommentResponseDto::of)
                        .collect(Collectors.toList()))
                .date(model.getPost().getCreatedAt())
                .build();
    }
}