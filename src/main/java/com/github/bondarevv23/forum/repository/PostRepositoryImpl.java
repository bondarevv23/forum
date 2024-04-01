package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.Post;
import com.github.bondarevv23.forum.exception.ServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepositoryImpl implements PostRepository {

    private static final String SELECT_BY_ID = "SELECT * FROM post_tab WHERE id = :id";
    private static final String DELETE = "DELETE FROM post_tab WHERE id = :id";
    private static final String SELECT_ALL = "SELECT * FROM post_tab";
    private static final String SELECT_BY_AUTHOR_ID = "SELECT * FROM post_tab WHERE author_id = :author_id";
    private static final String INSERT = "INSERT INTO post_tab (title, text, author_id, created_at, updated_at) VALUES (:title, :text, :author_id, :created_at, :updated_at) RETURNING id";
    private static final String UPDATE = "UPDATE post_tab SET title = :title, text = :text, updated_at = :updated_at WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Optional<Post> findById(Long id) {
        List<Post> users = jdbc.query(SELECT_BY_ID, getSqlSource("id", id), this::rowMapper);
        return switch (users.size()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(users.get(0));
            default -> {
                log.error("more than one value in table {} per ID {}", "post_tab", id);
                throw new ServerErrorException("inconsistent database state");
            }
        };
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update(DELETE, getSqlSource("id", id));
    }

    @Override
    public List<Post> findAll() {
        return jdbc.query(SELECT_ALL, this::rowMapper);
    }

    @Override
    public List<Post> findAllByAuthorId(Long authorId) {
        return jdbc.query(SELECT_BY_AUTHOR_ID, getSqlSource("author_id", authorId), this::rowMapper);
    }

    @Override
    public Post write(Post post) {
        Long id = jdbc.queryForObject(INSERT, getSqlSourcePost(post), Long.class);
        post.setId(id);
        return post;
    }

    @Override
    public void update(Post post) {
        jdbc.update(UPDATE, getSqlSourcePost(post));
    }

    private MapSqlParameterSource getSqlSource(String key, Object value) {
        return new MapSqlParameterSource(key, value);
    }

    private MapSqlParameterSource getSqlSourcePost(Post post) {
        return new MapSqlParameterSource()
                .addValue("id", post.getId())
                .addValue("title", post.getTitle())
                .addValue("text", post.getText())
                .addValue("author_id", post.getAuthorId())
                .addValue("created_at", post.getCreatedAt())
                .addValue("updated_at", post.getUpdatedAt());
    }

    private Post rowMapper(ResultSet rs, int rowNum) throws SQLException {
        if (rs == null) {
            return null;
        }
        return Post.builder()
                .id(rs.getLong("id"))
                .title(rs.getString("title"))
                .text(rs.getString("text"))
                .authorId(rs.getLong("author_id"))
                .createdAt(rs.getObject("created_at", LocalDateTime.class))
                .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
                .build();
    }
}
