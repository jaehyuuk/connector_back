package com.connector.service;

import com.connector.domain.Like;
import com.connector.domain.Post;
import com.connector.dto.CreatePostDto;
import com.connector.dto.PostDetailResponseDto;
import com.connector.dto.PostResponseDto;
import com.connector.global.exception.BadRequestException;
import com.connector.repository.PostRepository;
import com.connector.repository.model.GetPostRequestModel;
import com.connector.repository.model.GetPostResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostDetailResponseDto createPost(Long userId, CreatePostDto postDto) {
        Post post = postRepository.save(postDto.toEntity(userId));
        return getPostById(post.getId());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        List<GetPostResponseModel> posts = postRepository.getPosts(GetPostRequestModel.builder().build());

        return posts.stream()
                .map(PostResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDto getPostById(Long postId) {
        GetPostResponseModel post = postRepository.getPostById(GetPostRequestModel.builder()
                .postId(postId)
                .build()
        );
        return PostDetailResponseDto.of(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public void likePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException("Not Post")
        );

        boolean alreadyLike = post.getLikes()
                .stream()
                .map(Like::getUserId)
                .anyMatch(uid -> uid == userId);

        if (alreadyLike) {
            throw new BadRequestException("이미 좋아요를 누른 게시물입니다.");
        }

        post.addLike(Like.builder().userId(userId).build());
    }

    @Transactional
    public void unlikePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException("Not Post")
        );

        Map<Long, Like> likes = post.getLikes()
                .stream()
                .collect(Collectors.toMap(Like::getUserId, Function.identity()));

        if (!likes.containsKey(userId)) {
            throw new BadRequestException("좋아요 취소는 좋아요를 누른 게시물에만 가능합니다.");
        }
        post.removeLike(likes.get(userId));
    }

}