package com.connector.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToOne(fetch= FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // 참조를 당하는 쪽에서 읽기만 가능
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL) // 참조를 당하는 쪽에서 읽기만 가능
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Post(Long id, User user, String content, LocalDateTime createdAt, List<Like> likes, List<Comment> comments) {
        this.id = id;
        this.user = user;
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
}