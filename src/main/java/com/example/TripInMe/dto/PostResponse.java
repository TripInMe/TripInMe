package com.example.TripInMe.dto;

import com.example.TripInMe.repository.PostRepository;

import java.time.Instant;

public class PostResponse {
    public Long id;
    public String title;
    public String content;
    public Long authorId;
    public String authorUsername;
    public Instant createdAt;
    public Instant updatedAt;

    public PostResponse(Long id, String title, String content,Long authorId, String authorUsername,Instant createdAt, Instant updatedAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
