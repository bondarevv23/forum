package com.github.bondarevv23.forum.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static com.github.bondarevv23.forum.util.HibernateUtil.getEffectiveClass;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_tab")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 3)
    private Long id;

    private String nickname;

    private String email;

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime registeredAt = LocalDateTime.now();

    @OneToMany(mappedBy = "author")
    private Set<Post> posts;

    @Override
    public final boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getEffectiveClass(this) != getEffectiveClass(that)) {
            return false;
        }
        User user = (User) that;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public final int hashCode() {
        return getEffectiveClass(this).hashCode();
    }
}
