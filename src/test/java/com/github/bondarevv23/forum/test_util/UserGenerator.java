package com.github.bondarevv23.forum.test_util;

import com.github.bondarevv23.forum.domain.User;
import com.github.bondarevv23.forum.domain.generated.UserRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class UserGenerator {
    private static final LocalDateTime REGISTERED_TIME = LocalDateTime.of(1, 2, 3, 4, 5, 6);

    public static UserRequest getUserRequest(long i) {
        return UserRequest.builder()
                .nickname("user_" + i)
                .email("email_" + i + "@mail.com")
                .build();
    }

    public static User getUser(long i) {
        return User.builder()
                .nickname("user_" + i)
                .email("email_" + i + "@mail.com")
                .registeredAt(REGISTERED_TIME)
                .build();
    }

    public static Optional<User> getStoredUser(long i) {
        var user = getUser(i);
        user.setId(i);
        return Optional.of(user);
    }

    public static User getUser() {
        return getUser(ThreadLocalRandom.current().nextLong());
    }
}
