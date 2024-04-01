package com.github.bondarevv23.forum.controller;

import com.github.bondarevv23.forum.controller.api.PostApi;
import com.github.bondarevv23.forum.domain.generated.PostDTO;
import com.github.bondarevv23.forum.domain.generated.UpdatePostRequest;
import com.github.bondarevv23.forum.domain.generated.WritePostRequest;
import com.github.bondarevv23.forum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController implements PostApi {

    private final PostService service;

    @Override
    public ResponseEntity<Void> deletePostById(Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<PostDTO>> findAllPosts() {
        return ResponseEntity.ok(service.findAll());
    }

    @Override
    public ResponseEntity<List<PostDTO>> findAllPostsByAuthor(Long authorId) {
        return ResponseEntity.ok(service.findAllByAuthorId(authorId));
    }

    @Override
    public ResponseEntity<PostDTO> findPostById(Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    public ResponseEntity<Void> updatePostById(Long id, UpdatePostRequest updatePostRequest) {
        service.updateById(id, updatePostRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PostDTO> write(WritePostRequest writePostRequest) {
        return ResponseEntity.ok(service.write(writePostRequest));
    }
}
