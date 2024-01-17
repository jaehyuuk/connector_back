package com.connector.dto;

import com.connector.domain.Post;
import com.connector.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class CreatePostDto {
    @Schema(description = "내용", example = "Content")
    private String text;

    public Post toEntity(Long userId) {
        return new Post().builder()
                .user(User.builder().id(userId).build())
                .content(text)
                .createdAt(LocalDateTime.now())
                .likes(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }
}
