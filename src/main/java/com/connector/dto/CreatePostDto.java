package com.connector.dto;

import com.connector.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class CreatePostDto {
    private String text;

    public Post toEntity(Long userId) {
        return Post.builder()
                .userId(userId)
                .content(text)
                .createdAt(LocalDateTime.now())
                .likes(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }
}
