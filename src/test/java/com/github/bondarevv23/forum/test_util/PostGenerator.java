package com.github.bondarevv23.forum.test_util;

import com.github.bondarevv23.forum.domain.Post;
import com.github.bondarevv23.forum.domain.User;
import com.github.bondarevv23.forum.domain.generated.UpdatePostRequest;
import com.github.bondarevv23.forum.domain.generated.WritePostRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class PostGenerator {
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2, 3, 4, 5, 6, 7);

    public static Post getPost(User author, long i) {
        return Post.builder()
                .authorId(author.getId())
                .title("title_" + i)
                .text("text_" + i)
                .createdAt(CREATED_AT)
                .updatedAt(CREATED_AT)
                .build();
    }

    public static Optional<Post> getStoredPost(User author, long i) {
        var post = getPost(author, i);
        post.setId(i);
        return Optional.of(post);
    }

    public static Post getPost(User author) {
        return getPost(author, ThreadLocalRandom.current().nextLong());
    }

    public static WritePostRequest getWritePostRequest(User author, long i) {
        return WritePostRequest.builder()
                .authorId(author.getId())
                .title("title_" + i)
                .text("text_" + i)
                .build();
    }

    public static UpdatePostRequest getUpdatePostRequest(User author, long i) {
        return UpdatePostRequest.builder()
                .title("title_" + i)
                .text("text_" + i)
                .build();
    }
}
