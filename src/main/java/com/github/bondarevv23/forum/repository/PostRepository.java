package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByAuthorId(Long authorId);

}
