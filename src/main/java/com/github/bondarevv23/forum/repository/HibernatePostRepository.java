package com.github.bondarevv23.forum.repository;

import com.github.bondarevv23.forum.domain.Post;
import com.github.bondarevv23.forum.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Transactional
public class HibernatePostRepository implements PostRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(em.find(Post.class, id));
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    @Override
    public List<Post> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);

        criteriaQuery.select(postRoot);

        Query query = em.createQuery(criteriaQuery);
        @SuppressWarnings("unchecked") List<Post> posts = query.getResultList();
        return posts;
    }

    @Override
    public List<Post> findAllByAuthorId(Long authorId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);
        User author = em.getReference(User.class, authorId);
        Predicate authorIdPredicate = criteriaBuilder.equal(postRoot.get("author"), author);

        criteriaQuery.select(postRoot).where(authorIdPredicate);

        Query query = em.createQuery(criteriaQuery);
        @SuppressWarnings("unchecked") List<Post> posts = query.getResultList();
        return posts;
    }
}
