package com.connector.controller;

import com.connector.dto.CreatePostDto;
import com.connector.dto.PostDetailResponseDto;
import com.connector.dto.PostResponseDto;
import com.connector.global.context.TokenContext;
import com.connector.global.context.TokenContextHolder;
import com.connector.service.PostService;
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
    public PostDetailResponseDto createPost(@RequestBody CreatePostDto postDto) {
        TokenContext context = TokenContextHolder.getContext();
        Long userId = context.getUserId();
        return postService.createPost(userId, postDto);
    }

    @GetMapping
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/{post_id}")
    public PostDetailResponseDto getPostById(
            @PathVariable(value = "post_id") final Long postId
    ) {
        return postService.getPostById(postId);
    }

    @DeleteMapping("/{post-id}")
    public void deletePost(
            @PathVariable(value = "post-id") final Long postId
    ) {
        postService.deletePost(postId);
    }

    @PutMapping("/like/{post-id}")
    public void likePost(
            @PathVariable(value = "post-id") final Long postId
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
}