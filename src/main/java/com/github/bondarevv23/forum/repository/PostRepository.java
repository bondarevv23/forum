package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Optional<Post> findById(Long id);

    void deleteById(Long id);

    List<Post> findAll();

    List<Post> findAllByAuthorId(Long authorId);

    Post write(Post post);

    void update(Post post);

}
