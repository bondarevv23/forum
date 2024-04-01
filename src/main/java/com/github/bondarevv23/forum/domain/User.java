package com.github.bondarevv23.forum.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Long id;
    private String nickname;
    private String email;
    @Builder.Default
    private LocalDateTime registeredAt = LocalDateTime.now();
}
