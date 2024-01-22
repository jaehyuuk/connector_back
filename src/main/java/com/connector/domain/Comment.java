package com.connector.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name = "writer_id")
    private User writer;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void setPost(Post post) {
        if (this.post != null) {
            this.post.getComments().remove(this);
        }
        this.post = post;

        if (!post.getComments().contains(this)) {
            post.getComments().add(this);
        }
    }

    @Builder
    public Comment(Long id, Post post, User writer, String content, LocalDateTime createdAt) {
        this.id = id;
        this.post = post;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
    }
}