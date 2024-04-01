package com.github.bondarevv23.forum.service;

import com.github.bondarevv23.forum.domain.User;
import com.github.bondarevv23.forum.domain.generated.UserDTO;
import com.github.bondarevv23.forum.domain.generated.UserRequest;
import com.github.bondarevv23.forum.exception.UserNotFoundException;
import com.github.bondarevv23.forum.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    @Transactional
    @CacheEvict(value = "users")
    public void deleteById(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::userToUserDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "users")
    public UserDTO findById(Long id) {
        User stored = getOrThrow(id);
        return userToUserDTO(stored);
    }

    @Override
    @Transactional
    public UserDTO register(UserRequest userRequest) {
        User user = userRequestToUser(userRequest);
        return userToUserDTO(repository.save(user));
    }

    @Override
    @Transactional
    @CachePut(value = "users", key = "#id")
    public UserDTO updateById(Long id, UserRequest userRequest) {
        User stored = getOrThrow(id);
        User updated = updateUserByRequest(stored, userRequest);
        repository.save(updated);
        return userToUserDTO(updated);
    }

    private User getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(UserNotFoundException.withId(id));
    }

    private UserDTO userToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .registeredAt(user.getRegisteredAt())
                .build();
    }

    private User userRequestToUser(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .registeredAt(LocalDateTime.now())
                .build();
    }

    private User updateUserByRequest(User user, UserRequest request) {
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        return user;
    }
}
