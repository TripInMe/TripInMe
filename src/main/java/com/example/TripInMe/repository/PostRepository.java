package com.example.TripInMe.repository;

import com.example.TripInMe.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    @EntityGraph(attributePaths = "author")
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String titleKeyword, String contentKeyword, Pageable pageable
    );

    @Override
    @EntityGraph(attributePaths = "author")
    Page<Post> findAll(Pageable pageable);
}
