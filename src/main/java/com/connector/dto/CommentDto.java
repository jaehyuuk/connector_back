package com.connector.dto;

import com.connector.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @Schema(description = "내용", example = "Content")
    private String text;
    @Schema(description = "작성자", example = "Eric")
    private String name;
    @Schema(description = "유저 아바타")
    private String avatar;
    private Long userId;

    public static CommentDto getCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getWriter().getName(),
                comment.getWriter().getAvatar(),
                comment.getWriter().getId());
    }
}
