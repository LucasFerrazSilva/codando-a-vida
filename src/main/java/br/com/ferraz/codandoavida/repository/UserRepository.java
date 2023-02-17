package br.com.ferraz.codandoavida.repository;

import br.com.ferraz.codandoavida.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByStatusIs(String status);

}
