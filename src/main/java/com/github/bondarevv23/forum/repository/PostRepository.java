package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.Post;
import com.github.bondarevv23.forum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Optional<Post> findById(Long id);

    void deleteById(Long id);

    List<Post> findAll();

    List<Post> findAllByAuthorId(Long authorId);

}
