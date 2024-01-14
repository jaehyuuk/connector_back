package com.connector.repository.custom;

import com.connector.repository.model.GetPostRequestModel;
import com.connector.repository.model.GetPostResponseModel;

import java.util.List;

public interface PostRepositoryCustom {
    List<GetPostResponseModel> getPosts(GetPostRequestModel model);
    GetPostResponseModel getPostById(GetPostRequestModel model);
}