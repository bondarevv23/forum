package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.User;
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
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String SELECT_ALL = "SELECT * FROM user_tab";
    private static final String SELECT_BY_ID = "SELECT * FROM user_tab WHERE id = :id";
    private static final String INSERT = "INSERT INTO user_tab (nickname, email, registered_at) VALUES (:nickname, :email, :registered_at) RETURNING id";
    private static final String UPDATE = "UPDATE user_tab SET nickname = :nickname, email = :email WHERE id = :id";
    private static final String DELETE = "DELETE FROM user_tab WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Optional<User> findById(Long id) {
        List<User> users = jdbc.query(SELECT_BY_ID, getSqlSourceId(id), this::rowMapper);
        return switch (users.size()) {
            case 0 -> Optional.empty();
            case 1 -> Optional.of(users.get(0));
            default -> {
                log.error("more than one value in table {} per ID {}", "user_tab", id);
                throw new ServerErrorException("inconsistent database state");
            }
        };
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update(DELETE, getSqlSourceId(id));
    }

    @Override
    public List<User> findAll() {
        return jdbc.query(SELECT_ALL, this::rowMapper);
    }

    @Override
    public User register(User user) {
        Long id = jdbc.queryForObject(INSERT, getSqlSourceUser(user), Long.class);
        user.setId(id);
        return user;
    }

    @Override
    public void update(User user) {
        jdbc.update(UPDATE, getSqlSourceUser(user));
    }

    private MapSqlParameterSource getSqlSourceUser(User user) {
        return new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("nickname", user.getNickname())
                .addValue("email", user.getEmail())
                .addValue("registered_at", user.getRegisteredAt());
    }

    private MapSqlParameterSource getSqlSourceId(Object value) {
        return new MapSqlParameterSource("id", value);
    }

    private User rowMapper(ResultSet rs, int rowNum) throws SQLException {
        if (rs == null) {
            return null;
        }
        return User.builder()
                .id(rs.getLong("id"))
                .nickname(rs.getString("nickname"))
                .email(rs.getString("email"))
                .registeredAt(rs.getObject("registered_at", LocalDateTime.class))
                .build();
    }
}
