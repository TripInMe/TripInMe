package com.example.TripInMe.controller;

import com.example.TripInMe.dto.MessageResponse;
import com.example.TripInMe.dto.SendMessageRequest;
import com.example.TripInMe.dto.ThreadSummary;
import com.example.TripInMe.service.MessageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService svc;

    public MessageController(MessageService svc){this.svc=svc;}

    private static Long requireUserId(HttpSession session){
        Object id = session.getAttribute("USER_ID");
        if(id ==null) throw new IllegalStateException("로그인이 필요합니다.");
        return (Long) id;
    }

    @PostMapping
    public MessageResponse send(@RequestBody @Valid SendMessageRequest req, HttpSession session){
        return svc.send(requireUserId(session), req);
    }

    @GetMapping("/with/{userId}")
    public Page<MessageResponse> with(@PathVariable Long userId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "20") int size,
                                      HttpSession session){
        Pageable pageable = PageRequest.of(
                page,
                Math.min(size, 100),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return svc.conversation(requireUserId(session), userId, pageable);
    }

    @PostMapping("/{id}/read")
    public void markRead(@PathVariable Long id, HttpSession session){
        svc.markRead(requireUserId(session), id);
    }



    @GetMapping("/unread-count")
    public long unreadCount(HttpSession session) {
        return svc.unreadCount(requireUserId(session));
    }


    @GetMapping("/threads")
    public List<ThreadSummary> threads(HttpSession session) {
        return svc.threads(requireUserId(session));
    }

}
