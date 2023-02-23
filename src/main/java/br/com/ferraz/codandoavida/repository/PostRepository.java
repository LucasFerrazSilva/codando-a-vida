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

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(
        "SELECT p FROM Post p WHERE " +
            "(:title IS NULL OR p.title LIKE CONCAT('%', :title,'%')) " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:status IS NULL OR p.status = :status) " +
            "AND (:creationUser IS NULL OR p.creationUser = :creationUser) "
    )
    Page<Post> findAll(String title, Category category, PostStatus status, User creationUser, Pageable pageable);

    @Query(
        "SELECT DISTINCT p.creationUser FROM Post p"
    )
    List<User> findAuthors();

}
