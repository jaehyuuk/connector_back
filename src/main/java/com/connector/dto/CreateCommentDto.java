package com.connector.dto;

import com.connector.domain.Comment;
import com.connector.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentDto {
    private String text;

    public Comment toEntity(User writer) {
        return Comment.builder()
                .writer(writer)
                .content(text)
                .createdAt(LocalDateTime.now())
                .build();
    }
}