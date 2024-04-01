package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    List<User> findAll();

}
