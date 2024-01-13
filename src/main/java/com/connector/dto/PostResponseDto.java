package com.connector.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private Long id;
    private String text;
    private String name;
    private String avatar;
    private Long userId;
    private Integer likeCount;
    private Integer commentCount;
    private LocalDateTime date;
}