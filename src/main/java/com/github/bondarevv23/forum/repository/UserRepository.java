package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    void deleteById(Long id);

    List<User> findAll();

    User register(User user);

    void update(User user);

}
