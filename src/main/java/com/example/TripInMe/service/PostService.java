package com.example.TripInMe.service;


import com.example.TripInMe.dto.CreatePostRequest;
import com.example.TripInMe.dto.PostResponse;
import com.example.TripInMe.dto.UpdatePostRequest;
import com.example.TripInMe.entity.Post;
import com.example.TripInMe.entity.User;
import com.example.TripInMe.repository.PostRepository;
import com.example.TripInMe.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

@Service
public class PostService {
    private final PostRepository posts;
    private final UserRepository users;

    public PostService(PostRepository posts, UserRepository users){
        this.posts=posts;
        this.users=users;
    }

    @Transactional
    public PostResponse create(Long userId, CreatePostRequest req){
        User author = users.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post p = new Post();
        p.setAuthor(author);
        p.setTitle(req.title);
        p.setContent(req.content);
        Post saved = posts.save(p);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> list(String q, Pageable pageable){
        Page<Post> page = (q == null || q.isBlank())
                ? posts.findAll(pageable)
                : posts.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(q, q, pageable);
        return page.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public PostResponse get(Long id){
        Post p = posts.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다. "));

        return toResponse(p);
    }

    @Transactional
    public PostResponse update(Long userId, Long postId, UpdatePostRequest req) {
        Post p = posts.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if (!p.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("작성자만 수정할 수 있습니다.");
        }
        p.setTitle(req.title);
        p.setContent(req.content);
        return toResponse(p);
    }

    @Transactional
    public void delete(Long userId, Long postId) {
        Post p = posts.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if (!p.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("작성자만 삭제할 수 있습니다.");
        }
        posts.delete(p);
    }






    private PostResponse toResponse(Post p){
        return new PostResponse(
                p.getId(), p.getTitle(), p.getContent(),
                p.getAuthor().getId(), p.getAuthor().getUsername(),
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }

    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String msg) {super(msg);}
    }
}
