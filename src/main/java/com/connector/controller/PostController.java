package com.connector.controller;

import com.connector.dto.*;
import com.connector.global.context.TokenContext;
import com.connector.global.context.TokenContextHolder;
import com.connector.service.PostService;
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

@Tag(name="게시물 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시물 등록 API", description = "게시물을 등록할 수 있다.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostDetailDto.class))
                    )
            }
    )
    public PostDetailDto createPost(
            @RequestBody CreatePostDto postDto
    ) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        return postService.createPost(userId, postDto);
    }

    @GetMapping
    @Operation(summary = "전체 게시물 조회 API", description = "모든 게시물을 조회할 수 있다.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostDto.class))
                    )
            }
    )
    public List<PostDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{post_id}")
    @Operation(summary = "게시물 상세 조회 API", description = "게시물을 상세 조회할 수 있다.")
    @Parameter(name = "post_id", description = "post ID", in = ParameterIn.PATH)
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostDetailDto.class))
                    )
            }
    )
    public PostDetailDto getOnePost(
            @PathVariable("post_id") Long postId
    ) {
        return postService.getOnePost(postId);
    }

    @DeleteMapping("/{post_id}")
    @Operation(summary = "게시물 삭제 API", description = "게시물을 삭제 할 수 있다.")
    @Parameter(name = "post_id", description = "post ID", in = ParameterIn.PATH)
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostDto.class))
                    )
            }
    )
    public void deletePost(
            @PathVariable("post_id") Long postId
    ){
        postService.deletePost(postId);
    }

    @PutMapping("/like/{post_id}")
    public void likePost(
            @PathVariable("post_id") Long postId
    ) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        postService.likePost(userId, postId);
    }

    @PutMapping("/unlike/{post-id}")
    public void unlikePost(
            @PathVariable(value = "post-id") final Long postId
    ) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        postService.unlikePost(userId, postId);
    }

    @PostMapping("/comment/{post-id}")
    public List<CommentDto> addComment(
            @PathVariable(value = "post-id") final Long postId,
            @RequestBody CreateCommentDto commentDto
    ) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        return postService.addComment(userId, postId, commentDto);
    }

    @DeleteMapping("/comment/{post-id}/{comment-id}")
    public void removeComment(
            @PathVariable(value = "post-id") final Long postId,
            @PathVariable(value = "comment-id") final Long commentId
    ) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        postService.removeComment(userId, postId, commentId);
    }
}