package br.com.ferraz.codandoavida.repository;

import br.com.ferraz.codandoavida.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByStatusIs(String status);

    @Query(
        "SELECT u FROM User u " +
        "WHERE " +
            "u.status = :status " +
            "AND (:name IS NULL OR u.name LIKE CONCAT('%', :name, '%')) " +
            "and (:email IS NULL OR u.email LIKE CONCAT('%', :email, '%')) " +
            "and (:role IS NULL OR u.role LIKE CONCAT('%', :role, '%')) "
    )
    List<User> findByStatusAndNameAndEmailAndRole(
        @Param("status") String status,
        @Param("name") String name,
        @Param("email") String email,
        @Param("role") String role
    );

}
