package com.connector.dto;

import com.connector.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailDto {
    private Long id;
    @Schema(description = "내용", example = "Content")
    private String text;
    @Schema(description = "작성자", example = "Eric")
    private String name;
    @Schema(description = "유저 아바타")
    private String avatar;
    private Long userId;
    @Schema(description = "댓글", example = "Content")
    private List<CommentDto> comments;
    @Schema(description = "작성 날짜")
    private LocalDateTime date;

    public PostDetailDto(Post post) {
        id = post.getId();
        text = post.getContent();
        name = post.getUser().getName();
        avatar = post.getUser().getAvatar();
        userId = post.getUser().getId();
        comments = post.getComments().stream().map(CommentDto::getCommentDto).collect(Collectors.toList());
        date = post.getCreatedAt();
    }
}
