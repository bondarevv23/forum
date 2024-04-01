package com.github.bondarevv23.forum.service;

import com.github.bondarevv23.forum.domain.generated.UserDTO;
import com.github.bondarevv23.forum.domain.generated.UserRequest;

import java.util.List;

public interface UserService {

    void deleteById(Long id);

    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO register(UserRequest userRequest);

    UserDTO updateById(Long id, UserRequest userRequest);

}
