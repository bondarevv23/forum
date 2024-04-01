package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.User;
import com.github.bondarevv23.forum.test_util.UserGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.github.bondarevv23.forum.test_util.UserGenerator.getUser;
import static org.assertj.core.api.Assertions.assertThat;


@Testcontainers
@SpringBootTest
public class UserRepositoryImplIntegrationTest {
    @Autowired
    private UserRepository repository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0");

    @Test
    @Transactional
    @Rollback
    void whenFindUserByIdAndUserExists_thenMethodReturnsUser() {
        // given
        var user = getUser(1);
        user = repository.register(user);

        // when
        var stored = repository.findById(user.getId());

        // then
        assertThat(stored.isPresent()).isTrue();
        assertThat(stored.get()).isEqualTo(user);
    }

    @Test
    void whenFindUserByWrongId_thenMethodReturnsEmptyOptional() {
        // given
        Long wrongId = -1L;

        // when
        var stored = repository.findById(wrongId);

        // then
        assertThat(stored.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void whenDeleteById_thenRepositoryDeletesUser() {
        // given
        var user = getUser();
        user = repository.register(user);

        // when
        repository.deleteById(user.getId());

        // then
        var stored = repository.findById(user.getId());
        assertThat(stored.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 5, 10})
    @Transactional
    @Rollback
    void whenFindAll_thenMethodReturnsAllUsers(long n) {
        // given
        var userComparator = Comparator.comparingLong(User::getId);
        var users = LongStream.range(1, n + 1)
                .mapToObj(UserGenerator::getUser)
                .map(repository::register)
                .sorted(userComparator)
                .collect(Collectors.toList());

        // when
        var storedUsers = repository.findAll();
        storedUsers.sort(userComparator);

        // then
        assertThat(users).isEqualTo(storedUsers);
    }

    @Test
    @Transactional
    @Rollback
    void whenRegisterNewUser_thenRepositoryAddsUser() {
        // given
        var newUser = getUser();

        // when
        newUser = repository.register(newUser);

        // then
        var stored = repository.findById(newUser.getId());
        assertThat(stored.isPresent()).isTrue();
        assertThat(stored.get()).isEqualTo(newUser);
    }

    @Test
    @Transactional
    @Rollback
    void whenUpdateUser_thenUpdatedUserIsStored() {
        // given
        var user = repository.register(getUser());

        // when
        user.setEmail("new_email@mail.com");
        user.setNickname("new_nickname");
        repository.update(user);

        // then
        var stored = repository.findById(user.getId());
        assertThat(stored.isPresent()).isTrue();
        assertThat(stored.get()).isEqualTo(user);
    }
}
