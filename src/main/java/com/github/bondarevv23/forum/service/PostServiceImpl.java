package com.github.bondarevv23.forum.service;

import com.github.bondarevv23.forum.domain.Post;
import com.github.bondarevv23.forum.domain.User;
import com.github.bondarevv23.forum.domain.generated.PostDTO;
import com.github.bondarevv23.forum.domain.generated.UpdatePostRequest;
import com.github.bondarevv23.forum.domain.generated.WritePostRequest;
import com.github.bondarevv23.forum.exception.PostNotFoundException;
import com.github.bondarevv23.forum.exception.UserNotFoundException;
import com.github.bondarevv23.forum.repository.PostRepository;
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
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    @CacheEvict(value = "posts")
    public void deleteById(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::postToPostDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDTO> findAllByAuthorId(Long authorId) {
        return repository.findAllByAuthorId(authorId)
                .stream()
                .map(this::postToPostDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "posts")
    public PostDTO findById(Long id) {
        return postToPostDTO(getOrThrow(id));
    }

    @Override
    @Transactional
    @CachePut(value = "posts", key = "#id")
    public PostDTO updateById(Long id, UpdatePostRequest updatePostRequest) {
        Post stored = getOrThrow(id);
        Post updated = updatePostByRequest(stored, updatePostRequest);
        repository.save(updated);
        return postToPostDTO(updated);
    }

    @Override
    @Transactional
    public PostDTO write(WritePostRequest writePostRequest) {
        Post post = writeRequestToPost(writePostRequest);
        post = repository.save(post);
        return postToPostDTO(post);
    }

    private Post getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException.withId(id));
    }

    private PostDTO postToPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .authorId(post.getAuthor().getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private Post writeRequestToPost(WritePostRequest request) {
        return Post.builder()
                .author(getUser(request.getAuthorId()))
                .title(request.getTitle())
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private Post updatePostByRequest(Post post, UpdatePostRequest request) {
        post.setTitle(request.getTitle());
        post.setText(request.getText());
        post.setUpdatedAt(LocalDateTime.now());
        return post;
    }
}
