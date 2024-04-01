package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.Post;
import com.github.bondarevv23.forum.domain.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Comparator;
import java.util.stream.LongStream;

import static com.github.bondarevv23.forum.test_util.PostGenerator.getPost;
import static com.github.bondarevv23.forum.test_util.UserGenerator.getUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@SpringBootTest
public class PostRepositoryImplIntegrationTest {
    @Autowired
    private PostRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0");

    private User author;

    @BeforeEach
    void initAuthor() {
        author = userRepository.register(getUser(1));
    }

    @AfterEach
    void deleteAuthor() {
        repository.deleteById(author.getId());
    }

    @Test
    @Transactional
    @Rollback
    void whenFindPostByIdAndPostExists_thenMethodReturnsPost() {
        // given
        var post = getPost(author);
        post = repository.write(post);

        // when
        var stored = repository.findById(post.getId());

        // then
        assertThat(stored.isPresent()).isTrue();
        assertThat(stored.get()).isEqualTo(post);
    }

    @Test
    void whenFindPostByWWrongId_thenMethodReturnsEmptyOptional() {
        // given
        var wrongId = -1L;

        // when
        var stored = repository.findById(wrongId);

        // then
        assertThat(stored.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    @Rollback
    void whenDeletePostById_theRepositoryDeletesPost() {
        // given
        var post = repository.write(getPost(author));

        // when
        repository.deleteById(post.getId());

        // then
        var stored = repository.findById(post.getId());
        assertThat(stored.isEmpty()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 5, 10})
    @Transactional
    @Rollback
    void whenFindAllPosts_thenMethodReturnsAllStoredPosts(long n) {
        // given
        var postComparator = Comparator.comparingLong(Post::getId);
        var posts = LongStream.range(1, n + 1)
                .mapToObj(i -> getPost(author, i))
                .map(repository::write)
                .sorted(postComparator)
                .toList();

        // when
        var stored = repository.findAll();

        // then
        assertThat(stored).isEqualTo(posts);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 5, 10})
    @Transactional
    @Rollback
    void whenFindAllPostsByAuthorId_thenMethodReturnsAllPostsWrittenByAuthor(long n) {
        // given
        var postComparator = Comparator.comparingLong(Post::getId);
        var posts = LongStream.range(1, n + 1)
                .mapToObj(i -> getPost(author, i))
                .map(repository::write)
                .sorted(postComparator)
                .toList();
        LongStream.range(1, n + 1)
                .mapToObj(i -> getPost(userRepository.register(getUser(i)), i))
                .forEach(repository::write);

        // when
        var stored = repository.findAllByAuthorId(author.getId());

        // then
        assertThat(stored).isEqualTo(posts);
    }

    @Test
    @Transactional
    @Rollback
    void whenWriteNewPost_thenPostSavedToRepository() {
        // given
        var post = repository.write(getPost(author));

        // when
        var stored = repository.findById(post.getId());

        // then
        assertThat(stored.isPresent()).isTrue();
        assertThat(stored.get()).isEqualTo(post);
    }

    @Test
    void whenWriteNewPostWithWrongAuthorId_then() {
        // given
        var wrongAuthor = getUser();
        wrongAuthor.setId(-1L);
        var post = getPost(wrongAuthor);

        // when

        // then
        assertThatThrownBy(() -> repository.write(post)).isInstanceOf(DataAccessException.class);
    }

    @Test
    @Transactional
    @Rollback
    void whenUpdatePost_thenNewPostDataStored() {
        // given
        var post = repository.write(getPost(author));

        // when
        post.setText("new test");
        post.setTitle("new title");
        repository.update(post);

        // then
        var stored = repository.findById(post.getId());
        assertThat(stored.isPresent()).isTrue();
        assertThat(stored.get()).isEqualTo(post);
    }
}
