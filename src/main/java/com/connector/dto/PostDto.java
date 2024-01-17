package com.connector.dto;

import com.connector.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private Long id;
    @Schema(description = "내용", example = "Content")
    private String text;
    @Schema(description = "작성자", example = "Eric")
    private String name;
    @Schema(description = "유저 아바타")
    private String avatar;
    private Long userId;
    @Schema(description = "좋아요 횟수")
    private Integer likeCount;
    @Schema(description = "댓글 갯수")
    private Integer commentCount;
    @Schema(description = "작성 날짜")
    private LocalDateTime date;

    public PostDto(Post post) {
        id = post.getId();
        text = post.getContent();
        name = post.getUser().getName();
        avatar = post.getUser().getAvatar();
        userId = post.getUser().getId();
        likeCount = post.getLikes().size();
        commentCount = post.getComments().size();
        date = post.getCreatedAt();
    }
}
