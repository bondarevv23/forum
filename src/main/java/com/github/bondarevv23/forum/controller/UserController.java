package com.github.bondarevv23.forum.controller;

import com.github.bondarevv23.forum.controller.api.UserApi;
import com.github.bondarevv23.forum.domain.generated.UserDTO;
import com.github.bondarevv23.forum.domain.generated.UserRequest;
import com.github.bondarevv23.forum.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService service;

    @Override
    public ResponseEntity<Void> deleteUserById(Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<UserDTO> findUserById(Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    public ResponseEntity<UserDTO> register(UserRequest userRequest) {
        return ResponseEntity.ok(service.register(userRequest));
    }

    @Override
    public ResponseEntity<Void> updateUserById(Long id, UserRequest userRequest) {
        service.updateById(id, userRequest);
        return ResponseEntity.ok().build();
    }
}
