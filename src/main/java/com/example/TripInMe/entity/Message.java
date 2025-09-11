package com.example.TripInMe.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "messages", indexes = {
        @Index(name = "idx_msg_inbox", columnList = "recipient_id, id DESC"),
        @Index(name = "idx_msg_pair", columnList = "sender_id, recipient_id, id DESC"),
        @Index(name = "idx_msg_pair_rev", columnList = "recipient_id, sender_id, id DESC")
})
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "sender_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_msg_sender")
    )
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "recipient_id",
            nullable = false,
            foreignKey = @ForeignKey(name="fk_msg_recipient")
    )
    private User recipient;

    @Lob
    @Column(nullable = false)
    private String body;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name="read_at")
    private Instant readAt;

    @PrePersist
    void onCreate(){this.createdAt=Instant.now();}
}
