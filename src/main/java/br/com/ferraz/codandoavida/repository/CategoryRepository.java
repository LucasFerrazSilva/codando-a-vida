package br.com.ferraz.codandoavida.repository;

import br.com.ferraz.codandoavida.enums.Status;
import br.com.ferraz.codandoavida.model.Category;
import br.com.ferraz.codandoavida.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(
        "SELECT c FROM Category c WHERE " +
            "c.status = :status " +
            "AND (:name IS NULL OR c.name LIKE CONCAT('%', :name,'%')) " +
            "AND (:creationUser IS NULL OR c.creationUser = :creationUser) "
    )
    Page<Category> findByStatusAndNameAndCreationUser(Status status, String name, User creationUser, Pageable pageable);

}
