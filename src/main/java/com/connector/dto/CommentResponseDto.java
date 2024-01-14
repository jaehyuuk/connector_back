package com.connector.dto;

import com.connector.domain.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String text;
    private String name;
    private String avatar;
    private Long userId;

    @Builder
    public CommentResponseDto(Long id, String text, String name, String avatar, Long userId) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
    }

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .text(comment.getContent())
                .name(comment.getWriter().getName())
                .avatar(comment.getWriter().getAvatar())
                .userId(comment.getWriter().getId())
                .build();
    }
}