package com.github.bondarevv23.forum.service;

import com.github.bondarevv23.forum.domain.Post;
import com.github.bondarevv23.forum.domain.User;
import com.github.bondarevv23.forum.exception.UserNotFoundException;
import com.github.bondarevv23.forum.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import static com.github.bondarevv23.forum.test_util.PostGenerator.*;
import static com.github.bondarevv23.forum.test_util.UserGenerator.getStoredUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Profile("test")
public class PostServiceImplTest {

    @Mock
    PostRepository repository;

    @InjectMocks
    PostServiceImpl service;

    private User author = getStoredUser(1L).orElseThrow();

    @Test
    void whenDeletePostByWrightId_thenDeleteAndFindRepositoryMethodsAreCalled() {
        // given
        var captor = ArgumentCaptor.forClass(Long.class);
        when(repository.findById(1L)).thenReturn(getStoredPost(author, 1L));

        // when
        service.deleteById(1L);

        // then
        verify(repository, times(1)).findById(captor.capture());
        assertThat(captor.getValue()).isEqualTo(1L);
    }

    @Test
    void whenFindAll_thenFindAllRepositoryMethodIsCalled() {
        // given

        // when
        service.findAll();

        // then
        verify(repository, times(1)).findAll();
    }

    @Test
    void whenFindAllByAuthorId_thenFindAllByAuthorIdRepositoryMethodIsCalled() {
        // given
        var captor = ArgumentCaptor.forClass(Long.class);

        // when
        service.findAllByAuthorId(1L);

        // then
        verify(repository, times(1)).findAllByAuthorId(captor.capture());
        assertThat(captor.getValue()).isEqualTo(1L);
    }

    @Test
    void whenFindByRightId_thenFindByIdRepositoryMethodIsCalled() {
        // given
        when(repository.findById(1L)).thenReturn(getStoredPost(author, 1L));

        // when
        service.findById(1L);

        // then
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void whenFindByWrongId_thenUserNotFoundExceptionWasThrown() {
        // given
        when(repository.findById(-1L)).thenThrow(UserNotFoundException.class);

        // when

        // then
        assertThatThrownBy(() -> service.findById(-1L)).isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void whenWriteNewPost_thenRepositoryIsCalledAndPassedArgumentCorrespondsToRequest() {
        // given
        var request = getWritePostRequest(author, 1L);
        var captor = ArgumentCaptor.forClass(Post.class);
        when(repository.write(any(Post.class))).thenReturn(getStoredPost(author, 1L).orElseThrow());

        // when
        service.write(request);

        // then
        verify(repository, times(1)).write(captor.capture());
        assertThat(request.getTitle()).isEqualTo(captor.getValue().getTitle());
        assertThat(request.getText()).isEqualTo(captor.getValue().getText());
        assertThat(request.getAuthorId()).isEqualTo(captor.getValue().getAuthorId());
    }

    @Test
    void whenUpdateUser_thenRepositoryUpdateMethodIsCalledWithUpdatedPostData() {
        // given
        var captor = ArgumentCaptor.forClass(Post.class);
        var request = getUpdatePostRequest(author, 2L);
        when(repository.findById(1L)).thenReturn(getStoredPost(author, 1L));

        // when
        service.updateById(1L, request);

        // then
        verify(repository).update(captor.capture());
        assertThat(captor.getValue().getTitle()).isEqualTo(request.getTitle());
        assertThat(captor.getValue().getText()).isEqualTo(request.getText());
    }
}
