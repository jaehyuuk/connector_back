package com.connector.repository.custom;

import com.connector.dto.PostResponseDto;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostResponseDto> getPost();
}