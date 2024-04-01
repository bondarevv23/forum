package com.github.bondarevv23.forum.service;

import com.github.bondarevv23.forum.domain.User;
import com.github.bondarevv23.forum.exception.UserNotFoundException;
import com.github.bondarevv23.forum.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;

import static com.github.bondarevv23.forum.test_util.UserGenerator.getStoredUser;
import static com.github.bondarevv23.forum.test_util.UserGenerator.getUserRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Profile("test")
public class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void whenDeleteByRightId_thenRepositoryIsCalled() {
        // given
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        var user = getStoredUser(1L);
        when(repository.findById(1L)).thenReturn(user);

        // when
        service.deleteById(1L);

        // then
        verify(repository, times(1)).findById(captor.capture());
        assertThat(captor.getValue()).isEqualTo(1L);
        verify(repository, times(1)).deleteById(captor.capture());
        assertThat(captor.getValue()).isEqualTo(1L);
    }

    @Test
    void whenDeleteByWrongId_thenExceptionThrows() {
        // given
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        when(repository.findById(-1L)).thenThrow(UserNotFoundException.class);

        // when

        // then
        assertThatThrownBy(() -> service.deleteById(-1L)).isInstanceOf(UserNotFoundException.class);
        verify(repository, times(1)).findById(captor.capture());
        assertThat(captor.getValue()).isEqualTo(-1L);
        verify(repository, times(0)).deleteById(-1L);
    }

    @Test
    void whenFindAll_thenRepositoryFindAllMethodIsCalled() {
        // given

        // when
        service.findAll();

        // then
        verify(repository, times(1)).findAll();
    }

    @Test
    void whenFindByRightId_thenRepositoryIsCalled() {
        // given
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        var user = getStoredUser(1L);
        when(repository.findById(1L)).thenReturn(user);

        // when
        service.findById(1L);

        // then
        verify(repository, times(1)).findById(captor.capture());
        assertThat(captor.getValue()).isEqualTo(1L);
    }

    @Test
    void whenFindByWrongId_thenExceptionThrows() {
        // given
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        when(repository.findById(-1L)).thenThrow(UserNotFoundException.class);

        // when

        // then
        assertThatThrownBy(() -> service.findById(-1L)).isInstanceOf(UserNotFoundException.class);
        verify(repository, times(1)).findById(captor.capture());
    }

    @Test
    void whenRegisterNewUser_thenRepositoryIsCalledAndPassedArgumentCorrespondsToRequest() {
        // given
        var captor = ArgumentCaptor.forClass(User.class);
        var request = getUserRequest(1L);
        when(repository.save(any(User.class))).thenReturn(getStoredUser(1L).orElseThrow());

        // when
        service.register(request);

        // then
        verify(repository).save(captor.capture());
        var capturedUser = captor.getValue();
        assertThat(request.getEmail()).isEqualTo(capturedUser.getEmail());
        assertThat(request.getNickname()).isEqualTo(capturedUser.getNickname());
        assertThat(capturedUser.getRegisteredAt()).isNotNull();
    }

    @Test
    void whenUpdateUser_thenRepositoryUpdateMethodIsCalledWithUpdatedUserData() {
        // given
        var captor = ArgumentCaptor.forClass(User.class);
        var request = getUserRequest(2L);
        when(repository.findById(1L)).thenReturn(getStoredUser(1L));

        // when
        service.updateById(1L, request);

        // then
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo(request.getEmail());
        assertThat(captor.getValue().getNickname()).isEqualTo(request.getNickname());
    }
}
