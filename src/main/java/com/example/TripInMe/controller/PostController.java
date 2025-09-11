package com.example.TripInMe.controller;

import com.example.TripInMe.common.SessionUtil;
import com.example.TripInMe.dto.CreatePostRequest;
import com.example.TripInMe.dto.PostResponse;
import com.example.TripInMe.dto.UpdatePostRequest;
import com.example.TripInMe.service.PostService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService svc;

    public PostController(PostService svc){
        this.svc = svc;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody @Valid CreatePostRequest req, HttpSession session){
        Long userId = SessionUtil.requireUserId(session);
        PostResponse body = svc.create(userId, req);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public Page<PostResponse> list(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(required = false) String q){
        Pageable pageable = PageRequest.of(page, Math.min(size, 50), Sort.by(Sort.Direction.DESC, "id"));
        return svc.list(q, pageable);
    }

    @GetMapping("/{id}")
    public PostResponse get(@PathVariable Long id){
        return svc.get(id);
    }

    @PutMapping("/{id}")
    public PostResponse update(@PathVariable Long id,
                               @RequestBody @Valid UpdatePostRequest req,
                               HttpSession session){
        Long userId = SessionUtil.requireUserId(session);
        return svc.update(userId, id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, HttpSession session){
        Long userId = SessionUtil.requireUserId(session);
        svc.delete(userId, id);
    }
}
