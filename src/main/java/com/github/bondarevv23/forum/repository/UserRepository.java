package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
