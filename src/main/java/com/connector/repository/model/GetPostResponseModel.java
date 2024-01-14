package com.connector.repository.model;

import com.connector.domain.Post;
import com.connector.domain.User;
import lombok.Getter;

@Getter
public class GetPostResponseModel {
    private User user;
    private Post post;
}