package com.github.bondarevv23.forum.service;

import com.github.bondarevv23.forum.domain.generated.PostDTO;
import com.github.bondarevv23.forum.domain.generated.UpdatePostRequest;
import com.github.bondarevv23.forum.domain.generated.WritePostRequest;

import java.util.List;

public interface PostService {

    void deleteById(Long id);

    List<PostDTO> findAll();

    List<PostDTO> findAllByAuthorId(Long authorId);

    PostDTO findById(Long id);

    PostDTO updateById(Long id, UpdatePostRequest updatePostRequest);

    PostDTO write(WritePostRequest writePostRequest);

}
