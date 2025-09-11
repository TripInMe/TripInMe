package com.example.TripInMe.dto;

public class ThreadSummary {
    public Long partnerId;
    public Long lastMessageId;
    public Long unread;
    public ThreadSummary(Long partnerId, Long lastMessageId, Long unread){
        this.partnerId = partnerId;
        this.lastMessageId = lastMessageId;
        this.unread = unread;
    }
}
