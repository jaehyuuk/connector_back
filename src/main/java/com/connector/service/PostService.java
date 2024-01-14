package com.connector.service;

import com.connector.domain.Post;
import com.connector.dto.CreatePostDto;
import com.connector.dto.PostDetailResponseDto;
import com.connector.dto.PostResponseDto;
import com.connector.repository.PostRepository;
import com.connector.repository.model.GetPostRequestModel;
import com.connector.repository.model.GetPostResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostDetailResponseDto createPost(Long userId, CreatePostDto postDto) {
        Post post = postRepository.save(postDto.toEntity(userId));
        return getPostById(post.getId());
    }

    public List<PostResponseDto> getPosts() {
        List<GetPostResponseModel> posts = postRepository.getPosts(GetPostRequestModel.builder().build());

        return posts.stream()
                .map(PostResponseDto::of)
                .collect(Collectors.toList());
    }

    public PostDetailResponseDto getPostById(Long postId) {
        GetPostResponseModel post = postRepository.getPostById(GetPostRequestModel.builder()
                .postId(postId)
                .build()
        );
        return PostDetailResponseDto.of(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

}