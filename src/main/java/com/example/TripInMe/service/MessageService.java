package com.example.TripInMe.service;

import com.example.TripInMe.dto.MessageResponse;
import com.example.TripInMe.dto.SendMessageRequest;
import com.example.TripInMe.dto.ThreadSummary;
import com.example.TripInMe.entity.Message;
import com.example.TripInMe.entity.User;
import com.example.TripInMe.repository.MessageRepository;
import com.example.TripInMe.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messages;
    private final UserRepository users;

    public MessageService(MessageRepository messages, UserRepository users){
        this.messages = messages;
        this.users = users;
    }

    @Transactional
    public MessageResponse send(Long senderId, SendMessageRequest req){
        if(senderId.equals(req.toUserId)){
            throw new IllegalArgumentException("본인에게는 보낼 수 없습니다. ");
        }
        User sender = users.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        User recipient = users.findById(req.toUserId)
                .orElseThrow(() -> new IllegalArgumentException("수신자 없음"));

        Message m = new Message();
        m.setSender(sender);
        m.setRecipient(recipient);
        m.setBody(req.body);
        Message saved = messages.save(m);

        return toRes(saved);
    }

    @Transactional(readOnly = true)
    public Page<MessageResponse> conversation(Long me, Long other, Pageable pageable){
        return messages.findConversation(me, other, pageable).map(this::toRes);
    }

    @Transactional
    public void markRead(Long me, Long messageId){
        Message m = messages.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메세지 없음"));
        if(!m.getRecipient().getId().equals(me)){
            throw new IllegalStateException("수신자만 읽음 처리 가능");
        }
        if(m.getReadAt() == null){
            m.setReadAt(Instant.now());
        }
    }

    @Transactional(readOnly = true)
    public long unreadCount(Long me){
        return messages.countUnread(me);
    }

    @Transactional(readOnly = true)
    public List<ThreadSummary> threads(Long me) {
        return messages.listThreads(me).stream()
                .map(r -> new ThreadSummary(
                        ((Number) r[0]).longValue(), // partner_id
                        ((Number) r[1]).longValue(), // last_id
                        r[2] == null ? 0L : ((Number) r[2]).longValue() // unread
                ))
                .toList();
    }


    private MessageResponse toRes(Message m) {
        return new MessageResponse(
                m.getId(), m.getSender().getId(), m.getRecipient().getId(),
                m.getBody(), m.getCreatedAt(), m.getReadAt()
        );
    }
}
