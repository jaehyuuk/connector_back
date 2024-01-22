package com.connector.domain;

import com.connector.global.exception.BadRequestException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(Long id, Long userId, String content, LocalDateTime createdAt, List<Like> likes, List<Comment> comments) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.likes = likes;
        this.comments = comments;
    }

    public void addLike(Like like) {
        this.likes.add(like);
        if(like.getPost() != this) {
            like.setPost(this);
        }
    }

    public void removeLike(Like like) {
        Iterator<Like> iterator = this.likes.iterator();
        while (iterator.hasNext()) {
            Like e = iterator.next();
            if (like.equals(e)) {
                iterator.remove();
            }
        }
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        if(comment.getPost() != this) {
            comment.setPost(this);
        }
    }

    public void removeComment(Comment comment, Long userId) {
        if(comment.getWriter().getId() != userId) {
            throw new BadRequestException("댓글 삭제는 댓글 작성자만 가능합니다.");
        }

        Iterator<Comment> iterator = this.comments.iterator();
        while (iterator.hasNext()) {
            Comment e = iterator.next();
            if (comment.equals(e)) {
                iterator.remove();
            }
        }
    }
}