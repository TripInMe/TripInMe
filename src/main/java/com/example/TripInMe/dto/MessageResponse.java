package com.example.TripInMe.dto;

import java.time.Instant;

public class MessageResponse {
    public Long id;
    public Long senderId;
    public Long recipientId;

    public String body;

    public Instant createdAt;
    public Instant readAt;

    public MessageResponse(Long id, Long senderId, Long recipientId, String body,
                           Instant createdAt, Instant readAt){
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.body = body;
        this.createdAt = createdAt;
        this.readAt = readAt;
    }

}
