package com.connector.repository.custom;

import com.connector.repository.model.GetPostRequestModel;
import com.connector.repository.model.GetPostResponseModel;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.connector.domain.QPost.post;
import static com.connector.domain.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<GetPostResponseModel> getPosts(GetPostRequestModel model) {
        return getPostByModel(model)
                .orderBy(post.id.desc())
                .fetch();
    }

    @Override
    public GetPostResponseModel getPostById(GetPostRequestModel model) {
        return getPostByModel(model)
                .fetchOne();
    }

    private JPAQuery<GetPostResponseModel> getPostByModel(GetPostRequestModel model) {
        return queryFactory.select(Projections.fields(GetPostResponseModel.class
                        , user
                        , post
                )).from(post)
                .join(user).on(
                        post.userId.eq(user.id)
                )
                .where(
                        eqPostId(model.getPostId())
                );
    }

    private static BooleanExpression eqPostId(Long postId) {
        if (postId == null) return null;
        return post.id.eq(postId);
    }
}