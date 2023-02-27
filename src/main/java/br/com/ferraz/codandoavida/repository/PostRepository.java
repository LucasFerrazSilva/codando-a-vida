package br.com.ferraz.codandoavida.repository;

import br.com.ferraz.codandoavida.enums.PostStatus;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.model.Post;
import br.com.ferraz.codandoavida.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(
        "SELECT p FROM Post p WHERE " +
            "(:title IS NULL OR p.title LIKE CONCAT('%', :title,'%')) " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:status IS NULL OR p.status = :status) " +
            "AND (:creationUser IS NULL OR p.creationUser = :creationUser) " +
        "ORDER BY p.publishDate DESC"
    )
    Page<Post> findAll(String title, Category category, PostStatus status, User creationUser, Pageable pageable);

    @Query(
        "SELECT DISTINCT p.creationUser FROM Post p"
    )
    List<User> findAuthors();

    @Query(
        "SELECT p FROM Post p WHERE " +
            "p.status = :status AND (" +
                "(:search IS NULL OR p.title LIKE CONCAT('%', :search, '%')) " +
                "OR (:search IS NULL OR p.description LIKE CONCAT('%', :search, '%')) " +
                "OR (:search IS NULL OR p.body LIKE CONCAT('%', :search, '%')) " +
            ") " +
        "ORDER BY p.publishDate DESC "
    )
    Page<Post> findByStatusAndSearchOrderByPublishDateDesc(PostStatus status, String search, Pageable pageable);

    Optional<Post> findByTitle(String title);

    List<Post> findTop3ByCategoryAndStatusAndIdIsNotOrderByPublishDateDesc(Category category, PostStatus status, Integer id);

    Page<Post> findByCategoryAndStatusOrderByPublishDateDesc(Category category, PostStatus status, Pageable pageable);
}
