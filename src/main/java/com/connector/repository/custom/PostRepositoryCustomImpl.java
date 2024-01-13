package com.connector.repository.custom;

import com.connector.dto.PostResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.connector.domain.QPost.post;
import static com.connector.domain.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostResponseDto> getPost() {
        return queryFactory.select(Projections.fields(PostResponseDto.class
                        , post.id
                        , post.content.as("text")
                        , user.name
                        , user.avatar
                        , user.id.as("userId")
                        , post.likes.size().as("likeCount")
                        , post.comments.size().as("commentCount")
                        , post.createdAt.as("date")
                )).from(post)
                .join(user).on(
                        post.userId.eq(user.id)
                )
                .orderBy(post.id.desc())
                .fetch();
    }
}